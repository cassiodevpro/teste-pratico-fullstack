import { createRouter, createWebHistory } from 'vue-router';

import RawMaterialsView from '../views/RawMaterialsView.vue';
import ProductsView from '../views/ProductsView.vue';
import OptimizationView from '../views/OptimizationView.vue';
import HomeView from '../views/HomeView.vue';


const routes = [
  { path: '/', component: HomeView },
  { path: '/raw-materials', component: RawMaterialsView },
  { path: '/products', component: ProductsView },
  { path: '/optimize', component: OptimizationView }
];

export default createRouter({
  history: createWebHistory(),
  routes
});
