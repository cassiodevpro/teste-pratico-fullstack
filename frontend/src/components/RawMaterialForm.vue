<template>
  <form @submit.prevent="onSubmit" class="coffee-form">
    <label>Nome da matéria-prima</label>
    <input v-model="form.name" required />
    <label>Código</label>
    <input v-model="form.code" required />
    <label>Quantidade em estoque (kg)</label>
    <input v-model.number="form.stockQuantity" type="number" min="0" required />
    <button type="submit">Salvar</button>
  </form>
</template>
<script setup>
import { ref, watch } from 'vue';
const props = defineProps({ modelValue: Object });
const emit = defineEmits(['update:modelValue', 'submit']);
const form = ref({ ...props.modelValue });
watch(() => props.modelValue, v => form.value = { ...v });
function onSubmit() {
  emit('submit', { ...form.value });
}
</script>
<style scoped>
.coffee-form {
  max-width: 350px;
  margin: 2rem auto;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 0.7rem;
}
.dark .coffee-form select {
  background: #181818;
  color: #f5f3ea;
  border: 1px solid #444;
}
</style>
