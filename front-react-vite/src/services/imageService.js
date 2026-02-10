import apiClient from '@/api/axios';

// TODO: blob 타입 확인필요
const uploadImage = (blob) => {
  const formData = new FormData();
  formData.append('imageFile', blob);

  return apiClient.post('/image', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
};

const getImage = (path) => {
  return apiClient.get(`/image/${path}`);
};

export default { uploadImage, getImage };
