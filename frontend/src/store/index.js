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
      if(auth.state.user && auth.state.user.authorities) {
        return auth.state.user.authorities.length > 1;
      }
      return false;
    },
    loggedIn: state => {
      if(auth.state.status && auth.state.status.loggedIn) {
        return auth.state.status.loggedIn;
      }
      return false;
    }
  },
  modules: {
    auth
  }
})
