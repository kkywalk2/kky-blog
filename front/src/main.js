import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './vuex/store' // vuex 저장소 추가

import Buefy from 'buefy'
import 'buefy/dist/buefy.css'

Vue.use(Buefy)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
