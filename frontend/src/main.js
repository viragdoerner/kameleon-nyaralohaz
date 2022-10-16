import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import moment from 'vue-moment'
import VueAnalytics from 'vue-analytics';

// Configuration VueAnalytics
Vue.use(VueAnalytics, {
  id: 'G-4RPCRWVTWM',
  router
});

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  moment,
  render: h => h(App)
}).$mount('#app')
