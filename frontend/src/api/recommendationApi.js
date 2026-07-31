import api from './axiosConfig';

export const getRecommendations = async () => {
  const response = await api.get('/recommendations');
  return response.data;
};
