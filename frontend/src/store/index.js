import Vue from 'vue'
import Vuex from 'vuex'
import { auth } from './auth.module';
import { dialog } from './dialog.module';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    snackbar: {},
    imgPath: "./images/apartments/",
    bookingData: {},
    selectedApartmentId: null,
  },
  mutations: {
    showMessage(state, payload) {
      state.snackbar = payload;
    },
    saveBookingData(state, payload) {
      state.bookingData = payload;
    },
    selectApartmentId(state, id) {
      state.selectedApartmentId = id;
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
    getCurrentEmail: state => {
      return state.user ? state.user.username : "";
    },
    loggedIn: state => {
      if (auth.state.status && auth.state.status.loggedIn) {
        return auth.state.status.loggedIn;
      }
      return false;
    },
    getBookingData: state => {
      return state.bookingData;
    },
    getSelectedApartmentId: state => {
      return state.selectedApartmentId;
    },
    isApartmentSelected: state => {
      return state.selectedApartmentId != null;
    },
    isAvailableBookingData: state => {
      return Object.keys(state.bookingData).length !== 0;
    },
  },
  modules: {
    auth, dialog
  }
})
