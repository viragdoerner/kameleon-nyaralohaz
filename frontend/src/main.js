import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import moment from 'vue-moment'
import VueGtag from "vue-gtag";
import Cloudinary from 'cloudinary-vue'

Vue.use(VueGtag, {
  config: { id: "G-4RPCRWVTWM" }
}, router);

Vue.use(Cloudinary, {
  configuration: {
    cloudName: 'dtqjqi3ii'
  }
})

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  moment,
  render: h => h(App)
}).$mount('#app')
