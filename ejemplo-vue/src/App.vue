<template>
  <div id="app">    
    <NavBar></NavBar>
    <router-view/>    
  </div>
</template>

<script>
import NavBar from './components/NavBar'
export default {
  name: 'app',
  components:{
    'NavBar': NavBar
  }
  
}
</script>

<style lang="scss">
#app {
  font-family: Roboto, Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  
  color: #2c3e50;
}
</style>
