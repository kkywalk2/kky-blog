import apiClient from '@/api/axios';

const login = (accountName, password) => {
  const authInfo = {
    name: accountName,
    password,
  };

  return apiClient.post('/users/token', authInfo);
};

const signUp = (accountName, password, email) => {
  const signUpInfo = {
    name: accountName,
    password,
    email,
  };

  return apiClient.post('/users', signUpInfo);
};

const checkAuthentication = (token) => {
  return apiClient.get('/users');
};

export default {
  login,
  checkAuthentication,
  signUp,
};
