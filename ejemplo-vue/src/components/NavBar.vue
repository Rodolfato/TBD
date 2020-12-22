<template>
  <div id="navbar" class="container-fluid pt-1">  
        <nav class="navbar navbar-expand-sm navbar-dark bg-dark" >  
          
            <li class="nav-link"><img @click="exit" src="../assets/logo_verysmall.png"></li>
            <ul v-if="permission === ''" class="navbar-nav">
                <div class="nav-link">¡Bienvenidos futuros voluntarios...!</div>
             </ul>
            <ul v-if="permission === 'admin'" class="navbar-nav">
              <router-link class="nav-link navbar-inner" v-for="route in routesAdmin" 
                  v-bind:key="route.id"
                  :to="`${route.page}`">{{route.text}}
              </router-link>
             </ul>
             <ul v-if="permission === 'vol'" class="navbar-nav">
             <router-link class="nav-link navbar-inner" v-for="route in routesVol" 
                v-bind:key="route.id"
                :to="`${route.page}`">{{route.text}}
            </router-link>
            </ul>
             
            
                          
        </nav>
        <home/>
    </div>
    
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'NavBar',
  computed: {
    ...mapState(['permission'])

  },
  data() {
    return {
      routesVol: [
        {
          id: 0,
          text: 'Historial',
          page: '/historial'
        },
        {
          id: 1,
          text: 'Situación actual',
          page: '/sit-actual'
        },
        {
          id: 2,
          text: 'Inscribirse a emergencia',
          page: '/insc-eme'
        }
      ],
      routesAdmin: [
        {
          id: 0,
          text: 'Ver emergencias actuales',
          page:'/Emergencias'
        },
        {
          id: 1,
          text: 'Notificar una emergencia',
          page:'/new-emergencia'
        },
        {
          id: 2,
          text: 'Agregar una tarea',
          page: '/new-tarea'
        },
        {
          id: 3,
          text: 'Obtener voluntarios por radio',
          page: '/vol-radius'
        }
      ]
    }
  },
  methods: {
    exit: function(){
      this.$store.state.permission = '';
      window.location.href = '/';
    }
  }
}
</script>

<style>
.navbar-brand {
  padding: 0;
  background: transparent;
}
.navbar-nav .nav-link {
  padding-top: 0;
  padding-bottom: 0;
  height: 50px;
  line-height: 50px;
  background: transparent;
}

.navbar-inner {
    background: transparent;
}
</style>