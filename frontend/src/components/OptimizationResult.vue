<template>
  <div class="optimization-result">
    <h2>{{ $t('optimization.suggestion') }}</h2>
    <ul>
      <li v-for="prod in result.suggestedProducts" :key="prod.productName">
        <!-- Aqui pode-se traduzir os nomes dos produtos se necessário -->
        <strong>{{ prod.productName }}</strong>:
        {{ $t('optimization.quantity') }} {{ prod.quantityToProduce }} |
        {{ $t('optimization.totalRevenue') }} {{ prod.totalRevenue }}
      </li>
    </ul>
    <div class="total-revenue">
      {{ $t('optimization.totalExpectedRevenue') }}: <b>{{ result.totalExpectedRevenue }}</b>
    </div>
  </div>
</template>
<script setup>
const props = defineProps({ result: Object });
</script>
<style scoped>
.optimization-result {
  background: #f5f3ea;
  border-radius: 8px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px #c2b28033;
  margin-top: 1rem;
}
.optimization-result h2 {
  color: #6b4f27;
}
.optimization-result ul {
  list-style: none;
  padding: 0;
}
.optimization-result li {
  margin-bottom: 0.5rem;
  color: #4e3a1e;
}
.total-revenue {
  margin-top: 1rem;
  color: #8d6748;
  font-size: 1.1rem;
}
</style>
