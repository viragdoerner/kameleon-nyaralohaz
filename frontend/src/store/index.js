import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    admin: true,
    baseURL: "http://localhost:8080/",
    snackbar: { },
    rules: {
      required: value => !!value || 'Kötelező mező.',
      counter: value => value.length <= 50 || 'Max 20 characters',
      pricerange: value => value<1000000 || 'Túl nagy érték',
      email: value => {
        const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        return pattern.test(value) || 'Invalid e-mail.'
      },
    }
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
