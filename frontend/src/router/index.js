import Vue from 'vue'
import VueRouter from 'vue-router'
import CHome from '../views/Home.vue'
import CReserve from '../views/Reserve.vue'
import CLogin from '../views/Login.vue'
import CUserManagement from '../views/UserManagement.vue'

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
    path: '/user-management',
    name: 'User Management',
    component: CUserManagement
  },
]

const router = new VueRouter({
  routes,
  scrollBehavior(to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})

// router.beforeEach((to, from, next) => {
//   const publicPages = ['/login', '/register', '/home'];
//   const authRequired = !publicPages.includes(to.path);
//   const loggedIn = localStorage.getItem('user');
//   // trying to access a restricted page + not logged in
//   // redirect to login page
//   if (authRequired && !loggedIn) {
//     next('/login');
//   } else {
//     next();
//   }
// });

export default router
