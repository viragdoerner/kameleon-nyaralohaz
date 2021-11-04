import Vue from 'vue'
import VueRouter from 'vue-router'
import CHome from '../views/Home.vue'
import CReserve from '../views/Reserve.vue'
import CLogin from '../views/Login.vue'
import CPageNotFound from '../views/PageNotFound.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: CHome
  },
  {
    path: '/reserve',
    name: 'Reserve',
    component: CReserve
  },
  {
    path: '/login',
    name: 'Login',
    component: CLogin
  },
  {
    path: "*",
    component: CPageNotFound
  }
]

const router = new VueRouter({
  routes
})

export default router
