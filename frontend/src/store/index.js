import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    status: '',
    token: {},
    baseURL: "http://localhost:8080/",
    snackbar: {},
    imgPath: "./images/apartments/"
  },
  mutations: {
    showMessage(state, payload) {
      state.snackbar = payload;
    },
    auth_request(state) {
      state.status = 'loading'
    },
    auth_success(state, token) {
      state.status = 'success'
      state.token = token
    },
    auth_error(state) {
      state.status = 'error'
    },
    logout(state) {
      state.status = ''
      state.token = {}
    },
    initialiseStore(state) {
      if (!!localStorage.getItem('token')) {
        state.token = JSON.parse( localStorage.getItem('token'));
      }
    },
  },
  actions: {
    login({ commit }, loginForm) {
      return new Promise((resolve, reject) => {
        commit('auth_request')
        axios({ url: this.state.baseURL + "auth/signin", data: loginForm, method: 'POST' })
          .then(resp => {

            const token = {
              token: resp.data.tokenType + " " + resp.data.accessToken,
              isAdmin: (resp.data.authorities.length > 1),
              username: resp.data.username + ""
            };

            localStorage.setItem('token', JSON.stringify(token));

            axios.defaults.headers.common['Authorization'] = token.token;
            axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
            commit('auth_success', token);

            resolve(resp);
          })
          .catch(err => {
            commit('auth_error')
            localStorage.removeItem('token')
            reject(err)
          })
      })
    },
    logout({ commit }) {
      return new Promise((resolve, reject) => {
        commit('logout');
        localStorage.removeItem('token');
        delete axios.defaults.headers.common['Authorization']
        resolve()
      })
    },
    register({ commit }, registerForm) {
      return new Promise((resolve, reject) => {
        axios({ url: this.state.baseURL + "auth/signup", data: registerForm, method: 'POST' })
          .then(resp => {
            resolve(resp)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
  },
  getters: {
    isAdmin: state => {
      return !!state.token.isAdmin || false;
    },
    loggedIn: state => {
      return !!state.token || false;
    },
    authStatus: state => state.status
  },
  modules: {
  }
})
