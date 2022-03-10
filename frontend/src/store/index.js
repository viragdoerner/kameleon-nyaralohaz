import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    status: '',
    isAdmin: '',
    token: '',
    agreedToPrivacy: false,
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
    auth_success(state, token, isAdmin, user ) {
      state.status = 'success'
      state.isAdmin = isAdmin
      state.token = token
      state.user = user
    },
    auth_error(state) {
      state.status = 'error'
    },
    logout(state) {
      state.status = ''
      state.token = ''
      state.isAdmin = ''
    },
    agreePrivacyPolicy(state) {
      localStorage.setItem('agreedToPrivacy', true);
      state.agreedToPrivacy = true;
    },
    initialiseStore(state) {
      if (localStorage.getItem('token')) {
        state.token = localStorage.getItem('token');
      }
      if (localStorage.getItem('isAdmin')) {
        state.isAdmin = localStorage.getItem('isAdmin');
      }
      if (localStorage.getItem('agreedToPrivacy')) {
        state.agreedToPrivacy = true;
      }
    },
  },
  actions: {
    login({ commit }, loginForm) {
      return new Promise((resolve, reject) => {
        commit('auth_request')
        axios({ url: this.state.baseURL + "auth/signin", data: loginForm, method: 'POST' })
          .then(resp => {
          
            const token = resp.data.tokenType + " " + resp.data.accessToken;
            const user = resp.data.username + "";
            const isAdmin =  (resp.data.authorities.length > 1)? 'Admin' : '';

            localStorage.setItem('isAdmin', isAdmin);
            localStorage.setItem('token', resp.data.tokenType + " " + token);
            
            axios.defaults.headers.common['Authorization'] = token;
            axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
            commit('auth_success', token, isAdmin, user);           

            resolve(resp);
          })
          .catch(err => {
            commit('auth_error')
            localStorage.removeItem('token')
            localStorage.removeItem('isAdmin')
            reject(err)
          })
      })
    },
    logout({ commit }) {
      return new Promise((resolve, reject) => {
        commit('logout');
        localStorage.removeItem('token');
        localStorage.removeItem('isAdmin');
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
      return !!state.isAdmin || false;
    },
    loggedIn: state => {
      return !!state.token || false;
    },
    authStatus: state => state.status
  },
  modules: {
  }
})
