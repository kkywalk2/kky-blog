// src/vuex/getters.js
export default {
  getToken: state => state.token,
  getErrorState: state => state.errorState,
  getIsAuth: state => state.isAuth
}