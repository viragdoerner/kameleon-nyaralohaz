import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    status: '',
    token: localStorage.getItem('token') || '',
    role: localStorage.getItem('role') || '',
    user: {},
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
    auth_success(state, token, user, role) {
      state.status = 'success'
      state.token = token
      state.role = role
      state.user = user
    },
    auth_error(state) {
      state.status = 'error'
    },
    logout(state) {
      state.status = ''
      state.token = ''
      state.role = ''
    },
  },
  actions: {
    login({ commit }, loginForm) {
      return new Promise((resolve, reject) => {
        commit('auth_request')
        axios({ url: this.state.baseURL + "auth/signin", data: loginForm, method: 'POST' })
          .then(resp => {
            console.log("Vuex:");
            console.log("auth login elott: " + this.getters.loggedIn);
            console.log("role login elott: " + this.getters.getRole);

            const token = resp.data.tokenType + " " + resp.data.accessToken;
            const user = resp.data.username + "";
            const role =  (resp.data.authorities.length > 1) ? "Admin" : "User";

            localStorage.setItem('token', resp.data.tokenType + " " + token);
            localStorage.setItem('role', role);
            
            console.log("Vuex:");
            console.log("token: " + resp.data.tokenType + " " + token);
            console.log("role: " + role);

            axios.defaults.headers.common['Authorization'] = token;
            axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
            commit('auth_success', token, user, role);

            console.log("auth login utan: " + this.getters.loggedIn);
            console.log("role login utan: " + this.getters.getRole);

            resolve(resp);
          })
          .catch(err => {
            commit('auth_error')
            localStorage.removeItem('token')
            localStorage.removeItem('admin')
            reject(err)
          })
      })
    },
    logout({ commit }) {
      return new Promise((resolve, reject) => {
        commit('logout');
        localStorage.removeItem('token');
        localStorage.removeItem('role');
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
    getRole: state => {
      return state.role;
    },
    loggedIn: state => {
      return !!state.token || false;
    },
    authStatus: state => state.status
  },
  modules: {
  }
})
