<template>
  <div>
    <h1>{{ $t('rawMaterial.title') }}</h1>
    <RawMaterialForm v-model="selected" @submit="save" />
    <ul>
      <li v-for="mat in materials" :key="mat.id">
        {{ mat.name }} ({{ mat.code }}) - {{ mat.stockQuantity }}
        <button @click="edit(mat)">{{ $t('actions.edit') }}</button>
        <button @click="remove(mat.id)">{{ $t('actions.delete') }}</button>
      </li>
    </ul>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import { getRawMaterials, createRawMaterial, updateRawMaterial, deleteRawMaterial } from '../api/rawMaterials';
import RawMaterialForm from '../components/RawMaterialForm.vue';
const materials = ref([]);
const selected = ref({ name: '', code: '', stockQuantity: 0 });
async function load() {
  materials.value = (await getRawMaterials()).data;
}
onMounted(load);
function edit(mat) {
  selected.value = { ...mat };
}
async function save(mat) {
  if (mat.id) await updateRawMaterial(mat.id, mat);
  else await createRawMaterial(mat);
  selected.value = { name: '', code: '', stockQuantity: 0 };
  load();
}
async function remove(id) {
  await deleteRawMaterial(id);
  load();
}
</script>
