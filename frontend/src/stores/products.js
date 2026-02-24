import { ref } from 'vue';
import { getProducts } from '../api/products';

export const useProducts = () => {
  const products = ref([]);
  const load = async () => {
    products.value = (await getProducts()).data;
  };
  return { products, load };
};
