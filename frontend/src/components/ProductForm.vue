<template>
  <form @submit.prevent="onSubmit" class="coffee-form">
    <label>Nome do produto</label>
    <input v-model="form.name" required />
    <label>Código</label>
    <input v-model="form.code" required />
    <label>Preço</label>
    <input v-model.number="form.price" type="number" min="0" required />
    <slot name="ingredients"></slot>
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
  background: #f5f3ea;
  border-radius: 8px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px #c2b28033;
  display: flex;
  flex-direction: column;
  gap: 0.7rem;
  max-width: 350px;
  margin: 2rem auto;
  width: 100%;
}
.dark .coffee-form {
  background: #232323;
  box-shadow: 0 2px 8px #0008;
}
.dark .coffee-form label {
  color: #f5f3ea;
}
.dark .coffee-form input {
  background: #181818;
  color: #f5f3ea;
  border: 1px solid #444;
}
.dark .coffee-form button {
  background: #333;
  color: #f5f3ea;
}
.coffee-form label {
  color: #6b4f27;
  font-weight: bold;
}
.coffee-form input {
  border: 1px solid #bfa77a;
  border-radius: 4px;
  padding: 0.5rem;
}
.coffee-form button {
  background: #6b4f27;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 0.7rem;
  cursor: pointer;
  font-weight: bold;
}
.coffee-form button:hover {
  background: #8d6748;
}
</style>
