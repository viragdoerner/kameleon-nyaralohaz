import Vue from 'vue'
import Vuex from 'vuex'
import { auth } from './auth.module';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    baseURL: "http://localhost:8080/",
    snackbar: {},
    imgPath: "./images/apartments/"
  },
  mutations: {
    showMessage(state, payload) {
      state.snackbar = payload;
    }
  },
  actions: {

  },
  getters: {
    isAdmin: state => {
      return auth.state.user.authorities.length > 1;
    },
    loggedIn: state => {
      return auth.state.status.loggedIn;
    }
  },
  modules: {
    auth
  }
})
