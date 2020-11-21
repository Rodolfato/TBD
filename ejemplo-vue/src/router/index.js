import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Dogs from '../views/Dogs.vue'
import NewDog from '../views/NewDog.vue'
import Emergencias from '../views/Emergencias.vue'
import NewEmergencia from '../views/NewEmergencia.vue'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/dogs',
    name: 'Dogs',
    component: Dogs
  },
  {
    path: '/new-dog',
    name: 'NewDog',
    component: NewDog
  },
  {
    path: '/emergencias',
    name: 'Emergencias',
    component: Emergencias
  },
  {
    path: '/new-emergencia',
    name: 'NewEmergencia',
    component: NewEmergencia
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router