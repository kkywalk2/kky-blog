import axios from 'axios';

const login = (accountName: string, password: string) => {
  let authInfo = {
    accountName: accountName,
    password: password,
  };

  return axios.post(`/account/signin`, authInfo);
};

const signUp = (accountName: string, password: string, email: string) => {
  let signUpInfo = {
    accountName: accountName,
    password: password,
    email: email,
  };

  return axios.post(`/account/signup`, signUpInfo);
};

const checkAuthentication = (token: string | null) => {
  let headers = {
    'Content-type': 'application/json',
    Authorization: `${token}`,
  };

  console.log(token);

  return axios.get(`/account`, { headers });
};

export default {
  login,
  checkAuthentication,
  signUp,
};
