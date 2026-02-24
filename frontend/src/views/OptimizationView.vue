<template>
  <div>
    <h1>{{ t('optimization.title') }}</h1>
    <button @click="optimize">{{ t('optimization.run') }}</button>
    <OptimizationResult v-if="result" :result="result" />
  </div>
</template>
<script setup>
import { ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { getOptimization } from '../api/optimization';
import OptimizationResult from '../components/OptimizationResult.vue';
const { t } = useI18n();
const result = ref(null);
async function optimize() {
  result.value = (await getOptimization()).data;
}
</script>
