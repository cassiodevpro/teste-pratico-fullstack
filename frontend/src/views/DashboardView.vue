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

const optimization = ref({ suggestedProducts: [], totalExpectedRevenue: 0 });
const rawMaterials = ref([]);

onMounted(async () => {
  optimization.value = (await getOptimization()).data;
  rawMaterials.value = (await getRawMaterials()).data;
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
