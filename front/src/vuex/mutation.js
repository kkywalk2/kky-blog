// src/vuex/mutation.js
import * as types from './mutation_types'

export default {
  [types.TOKEN] (state, token) {
    localStorage.setItem("token",token)
  },
  [types.ERROR_STATE] (state, errorState) {
    state.errorState = errorState
  },
  [types.IS_AUTH] (state, isAuth) {
    state.isAuth = isAuth
  }
}