import Vue from 'vue'
import Vuex from 'vuex'
import { auth } from './auth.module';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    snackbar: {},
    imgPath: "./images/apartments/",
    booking: {}
  },
  mutations: {
    showMessage(state, payload) {
      state.snackbar = payload;
    },
    updateBooking(state, b) {
      state.booking = b;
    },
    cancelBooking(state) {
      state.booking = {};
    }
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
    getBooking: state => {
      return state.booking;
    }
  },
  modules: {
    auth
  }
})
