import axios from 'axios';

const API_URL = 'http://localhost:8080/api/raw-materials';

export const getRawMaterials = (params = {}) => axios.get(API_URL, { params });
export const getRawMaterial = (id) => axios.get(`${API_URL}/${id}`);
export const createRawMaterial = (data) => axios.post(API_URL, data);
export const updateRawMaterial = (id, data) => axios.put(`${API_URL}/${id}`, data);
export const deleteRawMaterial = (id) => axios.delete(`${API_URL}/${id}`);
