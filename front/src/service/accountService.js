import axios from 'axios'

const login = (accountName, password) => {
  let authInfo = {
    accountName: accountName,
    password: password
  };

  return axios.post(`${process.env.VUE_APP_SERVER_ADDRESS}/account/signin`,authInfo)
}

const signUp = (accountName, password, email) => {
  let signUpInfo = {
    accountName: accountName,
    password: password,
    email : email
  };

  return axios.post(`${process.env.VUE_APP_SERVER_ADDRESS}/account/signup`,signUpInfo)
}

const checkAuthentication = (token) => {
  let headers = {
    'Content-type': 'application/json',
    'Authorization': `${token}`
  }

  return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/account`,{headers})
}

export default {
  login,
  checkAuthentication,
  signUp
}