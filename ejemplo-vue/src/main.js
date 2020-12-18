import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'leaflet/dist/leaflet.css';
import "./assets/style/style.scss";
import { Icon } from 'leaflet';

delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

Vue.config.productionTip = false

//Instancia de axios se configura con URL base del backend
const axiosInstance = axios.create({ 
  baseURL: 'http://localhost:3000'
})

//Para acceder a axios desde this.$http
Vue.prototype.$http = axiosInstance;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
