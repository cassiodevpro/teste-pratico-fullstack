<template>
  <div class="dashboard">
    <h2 class="dashboard-title">Terminal da Fábrica - Sugestão de Produção</h2>
    <table class="dashboard-table dashboard-table-center">
      <thead>
        <tr>
          <th>Produto</th>
          <th>Quantidade Otimizada</th>
          <th>Receita Total</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="prod in optimization.suggestedProducts" :key="prod.productName">
          <td>{{ prod.productName }}</td>
          <td>{{ prod.quantityToProduce }} kg</td>
          <td>{{ prod.totalRevenue.toLocaleString('pt-BR', {minimumFractionDigits:2, maximumFractionDigits:2}) }}</td>
        </tr>
      </tbody>
    </table>
    <div class="total-revenue total-revenue-center">
      <b>Receita Esperada Total:</b> R$ {{ optimization.totalExpectedRevenue.toLocaleString('pt-BR', {minimumFractionDigits:2, maximumFractionDigits:2}) }}
    </div>
    <h2 class="dashboard-title" style="margin-top:2rem;">Estoque Atual</h2>
    <div style="margin-bottom: 1rem; display: flex; gap: 2rem; justify-content: center;">
      <div>
        <div style="font-weight:bold; margin-bottom:0.3rem;">Produtos</div>
        <div style="position:relative; display:inline-block; margin-right:0.5rem;">
          <input v-model="filterProductName" @input="onProductNameInput" @focus="showProductDropdown=true" @blur="hideDropdown('product')" placeholder="Filtrar por nome" style="padding: 0.4rem; border-radius: 4px; border: 1px solid #ccc; width:180px;" autocomplete="off" />
          <ul v-if="showProductDropdown && productSuggestions.length" style="position:absolute; z-index:10; background:#fff; border:1px solid #ccc; border-radius:4px; width:100%; max-height:160px; overflow:auto; margin:0; padding:0; list-style:none;">
            <li v-for="s in productSuggestions" :key="s.id" @mousedown.prevent="selectProductSuggestion(s)" style="padding:0.4rem; cursor:pointer;">{{ s.name }}</li>
          </ul>
        </div>
        <input v-model="filterProductCode" placeholder="Filtrar por código" style="padding: 0.4rem; border-radius: 4px; border: 1px solid #ccc;" />
      </div>
      <div>
        <div style="font-weight:bold; margin-bottom:0.3rem;">Matérias-primas</div>
        <div style="position:relative; display:inline-block; margin-right:0.5rem;">
          <input v-model="filterName" @input="onRawNameInput" @focus="showRawDropdown=true" @blur="hideDropdown('raw')" placeholder="Filtrar por nome" style="padding: 0.4rem; border-radius: 4px; border: 1px solid #ccc; width:180px;" autocomplete="off" />
          <ul v-if="showRawDropdown && rawSuggestions.length" style="position:absolute; z-index:10; background:#fff; border:1px solid #ccc; border-radius:4px; width:100%; max-height:160px; overflow:auto; margin:0; padding:0; list-style:none;">
            <li v-for="s in rawSuggestions" :key="s.id" @mousedown.prevent="selectRawSuggestion(s)" style="padding:0.4rem; cursor:pointer;">{{ s.name }}</li>
          </ul>
        </div>
        <input v-model="filterCode" placeholder="Filtrar por código" style="padding: 0.4rem; border-radius: 4px; border: 1px solid #ccc;" />
      </div>
      <div style="display:flex; flex-direction:column; justify-content:end; gap:0.5rem;">
        <button @click="applyFilter" style="padding: 0.4rem 1rem; border-radius: 4px; background: #6b4f27; color: #fff; border: none; margin-bottom:0.3rem;">Filtrar</button>
        <button @click="clearFilter" style="padding: 0.4rem 1rem; border-radius: 4px; background: #bfa77a; color: #fff; border: none;">Limpar</button>
      </div>
    </div>
    <table class="dashboard-table dashboard-table-center">
      <thead>
        <tr>
          <th>Produto</th>
          <th>Código</th>
          <th>Preço</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="prod in products" :key="prod.id">
          <td>{{ prod.name }}</td>
          <td>{{ prod.code }}</td>
          <td>R$ {{ prod.price?.toLocaleString('pt-BR', {minimumFractionDigits:2, maximumFractionDigits:2}) }}</td>
        </tr>
      </tbody>
    </table>
    <table class="dashboard-table dashboard-table-center">
      <thead>
        <tr>
          <th>Matéria-prima</th>
          <th>Estoque (kg)</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="mat in rawMaterials" :key="mat.id">
          <td>{{ mat.name }}</td>
          <td>{{ mat.stockQuantity }} kg</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import { getOptimization } from '../api/optimization';
import { getRawMaterials } from '../api/rawMaterials';
import { getProducts } from '../api/products';

const optimization = ref({ suggestedProducts: [], totalExpectedRevenue: 0 });
const rawMaterials = ref([]);
const products = ref([]);

const filterName = ref('');
const filterCode = ref('');
const filterProductName = ref('');
const filterProductCode = ref('');

const showProductDropdown = ref(false);
const showRawDropdown = ref(false);
const productSuggestions = ref([]);
const rawSuggestions = ref([]);

let productDropdownTimeout, rawDropdownTimeout;
async function onProductNameInput() {
  if (filterProductName.value.length > 0) {
    const res = await getProducts({ name: filterProductName.value });
    productSuggestions.value = res.data.slice(0, 8);
    showProductDropdown.value = true;
  } else {
    productSuggestions.value = [];
    showProductDropdown.value = false;
  }
}

function selectProductSuggestion(s) {
  filterProductName.value = s.name;
  showProductDropdown.value = false;
  applyFilter();
  // Após filtrar, mover o item selecionado para o topo
  setTimeout(() => {
    const idx = products.value.findIndex(p => p.id === s.id);
    if (idx > 0) {
      const [item] = products.value.splice(idx, 1);
      products.value.unshift(item);
    }
  }, 200);
}

function hideDropdown(type) {
  setTimeout(() => {
    if (type === 'product') showProductDropdown.value = false;
    if (type === 'raw') showRawDropdown.value = false;
  }, 150);
}

async function onRawNameInput() {
  if (filterName.value.length > 0) {
    const res = await getRawMaterials({ name: filterName.value });
    rawSuggestions.value = res.data.slice(0, 8);
    showRawDropdown.value = true;
  } else {
    rawSuggestions.value = [];
    showRawDropdown.value = false;
  }
}

function selectRawSuggestion(s) {
  filterName.value = s.name;
  showRawDropdown.value = false;
  applyFilter();
  // Após filtrar, mover o item selecionado para o topo
  setTimeout(() => {
    const idx = rawMaterials.value.findIndex(r => r.id === s.id);
    if (idx > 0) {
      const [item] = rawMaterials.value.splice(idx, 1);
      rawMaterials.value.unshift(item);
    }
  }, 200);
}

async function loadRawMaterials() {
  rawMaterials.value = (await getRawMaterials({ name: filterName.value, code: filterCode.value })).data;
}

async function loadProducts() {
  products.value = (await getProducts({ name: filterProductName.value, code: filterProductCode.value })).data;
}

function applyFilter() {
  loadRawMaterials();
  loadProducts();
}

function clearFilter() {
  filterName.value = '';
  filterCode.value = '';
  filterProductName.value = '';
  filterProductCode.value = '';
  loadRawMaterials();
  loadProducts();
}

onMounted(async () => {
  optimization.value = (await getOptimization()).data;
  loadRawMaterials();
  loadProducts();
});
</script>

<style scoped>
.buy-btn {
  background: #6b4f27;
  color: #fff;
  border: none;
  border-radius: 6px;
  padding: 0.6rem 1.2rem;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  margin-bottom: 1rem;
  transition: background 0.2s;
}
.buy-btn:hover {
  background: #8d6748;
}
.dashboard {
  padding: 2rem;
  background: #f5f3ea;
  border-radius: 12px;
  box-shadow: 0 2px 8px #c2b28033;
  margin: 2rem auto;
  max-width: 900px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.dashboard-title {
  text-align: center;
  margin-bottom: 1.5rem;
  color: #6b4f27;
}
.dashboard-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1.5rem;
}
.dashboard-table-center {
  margin-left: auto;
  margin-right: auto;
}
.dashboard-table th, .dashboard-table td {
  border: 1px solid #bfa77a;
  padding: 0.7rem 1rem;
  text-align: center;
}
.dashboard-table th {
  background: #e6d3b3;
  color: #6b4f27;
}
.dashboard-table td {
  background: #fff;
  color: #4e3a1e;
}
.total-revenue {
  font-size: 1.2rem;
  color: #8d6748;
  margin-bottom: 2rem;
}
.total-revenue-center {
  text-align: center;
}
</style>
