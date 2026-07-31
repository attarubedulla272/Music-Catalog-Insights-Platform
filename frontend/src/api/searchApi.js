import api from './axiosConfig';

export const searchCatalog = async (query, type = 'album', limit = 25) => {
  const response = await api.get('/search', {
    params: { query, type, limit },
  });
  return response.data;
};

export const lookupCatalogItem = async (id) => {
  const response = await api.get(`/search/lookup/${id}`);
  return response.data;
};
