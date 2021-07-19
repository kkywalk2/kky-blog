import axios from 'axios'

const getAuthToken = (accountName, password) => {
  let authInfo = {
    accountName: accountName,
    password: password
  };

  return axios.post('http://localhost:9000/account/signin',authInfo)
}

export default {
  async login (accountName, password) {
    try {
      const getUserInfoPromise = await getAuthToken(accountName, password)
      const [userInfoResponse] = await Promise.all([getUserInfoPromise])
      if (userInfoResponse.status !== 200 || userInfoResponse.data.status !== "OK") return 'noAuth'
      return userInfoResponse
    } catch (err) {
      console.error(err)
    }
  }
}