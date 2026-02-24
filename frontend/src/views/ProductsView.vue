<template>
  <div>
    <h1>{{ $t('product.title') }}</h1>
    <ProductForm v-model="selected" @submit="save">
      <template #ingredients>
        <div v-for="(ing, idx) in selected.ingredients || []" :key="idx">
          <label>{{ $t('product.ingredient') }} {{ idx + 1 }}</label>
          <input v-model="ing.rawMaterialId" placeholder="Raw Material ID" />
          <input v-model.number="ing.requiredQuantity" type="number" min="0" placeholder="Required Quantity" />
        </div>
        <button type="button" @click="addIngredient">{{ $t('product.addIngredient') }}</button>
      </template>
    </ProductForm>
    <ul>
      <li v-for="prod in products" :key="prod.id">
        {{ prod.name }} ({{ prod.code }}) - {{ prod.price }}
        <button @click="edit(prod)">{{ $t('actions.edit') }}</button>
        <button @click="remove(prod.id)">{{ $t('actions.delete') }}</button>
      </li>
    </ul>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import { getProducts, createProduct, updateProduct, deleteProduct } from '../api/products';
import ProductForm from '../components/ProductForm.vue';
const products = ref([]);
const selected = ref({ name: '', code: '', price: 0, ingredients: [] });
async function load() {
  products.value = (await getProducts()).data;
}
onMounted(load);
function edit(prod) {
  selected.value = { ...prod };
}
async function save(prod) {
  if (prod.id) await updateProduct(prod.id, prod);
  else await createProduct(prod);
  selected.value = { name: '', code: '', price: 0, ingredients: [] };
  load();
}
async function remove(id) {
  await deleteProduct(id);
  load();
}
function addIngredient() {
  selected.value.ingredients.push({ rawMaterialId: '', requiredQuantity: 0 });
}
</script>
