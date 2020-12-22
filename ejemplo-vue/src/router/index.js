import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Emergencias from '../views/Emergencias.vue'
import NewEmergencia from '../views/NewEmergencia.vue'
import HomePage from '../components/HomePage.vue'
import About from '../views/About.vue'
import NewTarea from '../views/NewTarea.vue'
import VolsByRadius from '../views/GetVolsByRadius.vue'
import InscEme from '../components/JoinEme.vue'
import SitActual from '../components/OngoingEmes.vue'
import Historial from '../components/EmeRecord.vue'


Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/insc-eme',
    name: 'InscEme',
    component: InscEme
  },
  {
    path: '/historial',
    name: 'Historial',
    component: Historial
  },
  {
    path: '/sit-actual',
    name: 'SitActual',
    component: SitActual
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
    path: '/homepage',
    name: 'HomePage',
    component: HomePage
  },
  {
    path: '/new-tarea',
    name: 'NewTarea',
    component: NewTarea
  },
  {
    path: '/vol-radius',
    name: 'GetVolsByRadius',
    component: VolsByRadius
  },
  {
    path: '/about',
    name: 'About',
    component: About
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    //component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
