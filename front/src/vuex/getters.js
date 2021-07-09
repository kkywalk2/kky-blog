// src/vuex/getters.js
export default {
  getToken: () => localStorage.getItem("token"),
  getErrorState: state => state.errorState,
  getIsAuth: state => state.isAuth
}