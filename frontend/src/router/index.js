import Vue from 'vue'
import VueRouter from 'vue-router'
import CHome from '../views/Home.vue'
import CBooking from '../views/Booking.vue'
import CLogin from '../views/Login.vue'
import CUserManagement from '../views/UserManagement.vue'
import CUserBooking from '../views/UserBooking.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: CHome
  },
  {
    path: '/booking',
    name: 'Booking',
    component: CBooking,
    props: { bookingData: {} }
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
  {
    path: '/user-booking',
    name: 'User Booking',
    component: CUserBooking
  }
]

const router = new VueRouter({
  routes,
  scrollBehavior(to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})

router.beforeEach((to, from, next) => {
  const adminPages = ['/user-management'];
  const userPages = ['/booking', '/user-management', '/user-booking'];
  const adminAuthRequired = adminPages.includes(to.path);
  const authRequired = userPages.includes(to.path);

  const user = JSON.parse(localStorage.getItem('user'));

  var loggedIn = false;
  var isAdmin = false;
  if (!!user) {
    loggedIn = true;
    if (user.authorities.length > 1) {
      isAdmin = true;
    }
  }

  if ((authRequired && !loggedIn) || (adminAuthRequired && !isAdmin)) {
    next('/login');
  } else {
    next();
  }
});

export default router
