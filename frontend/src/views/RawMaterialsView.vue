<template>
  <div class="raw-materials-container">
    <h1 class="raw-materials-title">{{ $t('rawMaterial.title') }}</h1>
    <RawMaterialForm v-model="selected" @submit="save" />
    <table class="raw-materials-table">
      <thead>
        <tr>
          <th>{{ $t('rawMaterial.name') }}</th>
          <th>{{ $t('rawMaterial.code') }}</th>
          <th>{{ $t('rawMaterial.stockQuantity') }}</th>
          <th>{{ $t('actions.edit') }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="mat in materials" :key="mat.id">
          <td>{{ mat.name }}</td>
          <td>{{ mat.code }}</td>
          <td>{{ mat.stockQuantity }}</td>
          <td>
            <button @click="edit(mat)">{{ $t('actions.edit') }}</button>
            <button @click="remove(mat.id)">{{ $t('actions.delete') }}</button>
          </td>
        </tr>
      </tbody>
    </table>
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
  <style scoped>
  .raw-materials-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 2rem;
  }
  .raw-materials-title {
    text-align: center;
    margin-bottom: 1.5rem;
    color: #6b4f27;
  }
  .raw-materials-table {
    margin-top: 2rem;
    border-collapse: collapse;
    width: 100%;
    max-width: 700px;
    background: #f5f3ea;
    box-shadow: 0 2px 8px #c2b28033;
  }
  .raw-materials-table th,
  .raw-materials-table td {
    text-align: center;
    padding: 0.7rem;
  }
  .raw-materials-table th {
    background: #e6d3b3;
    color: #6b4f27;
  }
  .raw-materials-table tr:nth-child(even) {
    background: #f9f6f2;
  }
  .raw-materials-table tr:hover {
    background: #f1e9d7;
  }
  </style>
