import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    admin: true,
    baseURL: "http://localhost:8080/",
    snackbar: { active: false, color: "", message: "" }
  },
  mutations: {
    SET_SNACKBAR(state, snackbar) {
      state.snackbar = snackbar;
    }
  },
  actions: {
    snackBar({ commit }, message) {
      commit("SET_SNACKBAR", {
        active: true,
        color: "success", // You can create another actions for diferent color.
        message: message
      });
    }
  },
  modules: {
  }
})
