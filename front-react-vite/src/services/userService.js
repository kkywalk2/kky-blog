import axios from 'axios';

const login = (accountName, password) => {
  let authInfo = {
    accountName: accountName,
    password: password,
  };

  return axios.post(`/users/token`, authInfo);
};

const signUp = (accountName, password, email) => {
  let signUpInfo = {
    accountName: accountName,
    password: password,
    email: email,
  };

  return axios.post(`/users`, signUpInfo);
};

const checkAuthentication = (token) => {
  let headers = {
    'Content-type': 'application/json',
    Authorization: `${token}`,
  };

  console.log(token);

  return axios.get(`/users`, { headers });
};

export default {
  login,
  checkAuthentication,
  signUp,
};
