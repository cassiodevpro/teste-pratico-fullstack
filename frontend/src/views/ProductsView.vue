<template>
  <div class="raw-materials-container">
    <h1 style="text-align: center; margin-bottom: 24px;">{{ $t('product.title') }}</h1>
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
    <table class="products-table" style="margin: 0 auto; border-collapse: collapse; width: 40%; box-shadow: 0 2px 8px #05030033;">
      <thead>
      <tr>
        <th style="text-align: center; padding: 8px;">{{ $t('product.name') }}</th>
        <th style="text-align: center; padding: 8px;">{{ $t('product.code') }}</th>
        <th style="text-align: center; padding: 8px;">{{ $t('product.price') }}</th>
        <th style="text-align: center; padding: 8px;">{{ $t('actions.edit') }}</th>
        <!-- <th style="text-align: center; padding: 8px;">{{ $t('actions.delete') }}</th> -->
      </tr>
      </thead>
      <tbody>
      <tr v-for="prod in products" :key="prod.id" style="border-bottom: 1px solid #ddd;">
        <td style="text-align: center; padding: 8px;">{{ prod.name }}</td>
        <td style="text-align: center; padding: 8px;">{{ prod.code }}</td>
        <td style="text-align: center; padding: 8px;">{{ prod.price }}</td>
        <td style="text-align: center; padding: 8px;">
        <button @click="edit(prod)" style="margin-right: 4px;">{{ $t('actions.edit') }}</button>
        <button @click="remove(prod.id)">{{ $t('actions.delete') }}</button>
        </td>
      </tr>
      </tbody>
    </table>
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
