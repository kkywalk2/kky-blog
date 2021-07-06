// src/service/index.js
import loginAPI from './loginAPI'

export default {
  async login (accountName, password) {
    try {
      const loginResponse = await loginAPI.login(accountName, password)
      return loginResponse
    } catch (err) {
      console.error(err)
    }
  }
}