import api from './axiosConfig';

export const getAnalyticsData = async () => {
  const response = await api.get('/analytics');
  return response.data;
};
