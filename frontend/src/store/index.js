import Vue from 'vue'
import Vuex from 'vuex'
import { auth } from './auth.module';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    snackbar: {},
    imgPath: "./images/apartments/",
    bookingData: {}
  },
  mutations: {
    showMessage(state, payload) {
      state.snackbar = payload;
    },
    saveBookingData(state, payload) {
      state.bookingData = payload;
    },
  },
  actions: {
  },
  getters: {
    isAdmin: state => {
      if (auth.state.user && auth.state.user.authorities) {
        return auth.state.user.authorities.length > 1;
      }
      return false;
    },
    loggedIn: state => {
      if (auth.state.status && auth.state.status.loggedIn) {
        return auth.state.status.loggedIn;
      }
      return false;
    },
    getBookingData: state => {
      return state.bookingData;
    }
  },
  modules: {
    auth
  }
})
