import { ref } from 'vue';
import { getRawMaterials } from '../api/rawMaterials';

export const useRawMaterials = () => {
  const materials = ref([]);
  const load = async () => {
    materials.value = (await getRawMaterials()).data;
  };
  return { materials, load };
};
