import api from './axiosConfig';

export const getLibrary = async (page = 0, size = 12, sortBy = 'createdAt', sortDir = 'desc') => {
  const response = await api.get('/library', {
    params: { page, size, sortBy, sortDir },
  });
  return response.data;
};

export const addAlbumToLibrary = async (albumData) => {
  const response = await api.post('/library', albumData);
  return response.data;
};

export const updateLibraryAlbum = async (id, updateData) => {
  const response = await api.put(`/library/${id}`, updateData);
  return response.data;
};

export const removeAlbumFromLibrary = async (id) => {
  const response = await api.delete(`/library/${id}`);
  return response.data;
};

export const getLibraryAlbum = async (id) => {
  const response = await api.get(`/library/${id}`);
  return response.data;
};
