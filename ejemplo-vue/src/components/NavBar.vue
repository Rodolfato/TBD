<template>
  <div id="navbar" class="container-fluid pt-1">  
        <nav class="navbar navbar-expand-sm navbar-dark bg-dark" >  
          <ul class="navbar-nav">
            <li class="nav-link"><img src="../assets/logo_verysmall.png"></li>            
             <router-link class="nav-link navbar-inner" v-for="route in routes" 
                v-bind:key="route.id"
                :to="`${route.page}`">{{route.text}}</router-link>              
            </ul>   
        </nav>
    </div>
</template>

<script>
export default {
  name: 'NavBar',
  data() {
    return {
      routes: [
        {
          id: 0,
          text: 'Home',
          page:'/'
        },
        {
          id: 1,
          text: 'Emergencias',
          page:'/Emergencias'
        },
        {
          id: 2,
          text: 'Nueva Emergencia',
          page:'/new-emergencia'
        },
        {
          id: 3,
          text: 'Map',
          page:'/map-test'
        },
      ]
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