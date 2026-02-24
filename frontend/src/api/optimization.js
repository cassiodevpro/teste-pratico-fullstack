import axios from 'axios';

const API_URL = 'http://localhost:8080/api/production/optimize';

export const getOptimization = () => axios.get(API_URL);
