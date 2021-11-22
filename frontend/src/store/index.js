import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    admin: true,
    baseURL: "http://localhost:8080/",
    snackbar: { }
  },
  mutations: {
    showMessage(state, payload) {
      state.snackbar = payload;
    }
  },
  actions: {
  },
  modules: {
  }
})
