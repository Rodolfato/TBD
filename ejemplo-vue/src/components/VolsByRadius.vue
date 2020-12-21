<template>
<div class="container-fluid">
    <h1 class="text-center pt-4">Buscar voluntarios cercanos a una tarea</h1>
    <div class="row container-fluid">

        <div class="col container-fluid">

            <form>
                 <div class="form-group">
                    <label for="id_emergencia">Elegir la emergencia</label>
                    <select class="form-control" id="id_emergencia" v-model="selectedEmeId">                        
                        <option v-bind:value="emergencia.id" v-for="(emergencia, index) in emes" :key="'emeFV' + index">{{emergencia.nombre}}</option>                        
                    </select>
                </div>


                 <div v-if="selectedEmeId != null">
                    <div class="form-group">
                        <label for="id_tarea">Elegir la tarea</label>
                        <select class="form-control" id="id_tarea" v-model="searchTarea.id">                        
                            <option v-bind:value="tarea.id" v-for="(tarea, index) in tareas" :key="'tareaFV' + index">{{tarea.nombre}}</option>                        
                        </select>
                    </div>
                </div> 

                <div v-if="this.tareas.length != 0">
                    <label for="radio">Elegir el radio de la búsqueda [km]</label>
                    <input class="form-control" type="number" min=1 id="radio" v-model="searchTarea.radio">
                </div>

                <div v-if="searchTarea.radio != null">
                    <div class="form-group text-center pt-4">
                        <button id="but-sub" type="button" class="btn btn-lg btn-primary" @click="getVols">Realizar búsqueda</button>
                    </div>
                </div>
            </form>

            


        </div>

        <div class="col">

                    <!-- componente Map.vue -->
        <div v-if="foundTarea != null" id="map-eme" class="col-lg map container-fluid">
            
            <l-map :zoom="zoom" :center="center" @click="onClick">
                <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>

                  

                <l-marker
                        :key="index"
                        v-for="(vol, index) in foundVols"
                        :lat-lng="latLng(vol.latitud, vol.longitud)"
                    >
                        <l-popup :content="'Voluntario: '+ vol.nombre + '</br>' + 'E-mail: ' + vol.email + '</br>' + 'Género: ' + vol.sexo"></l-popup>
                        <l-icon
                            :icon-size="volSize"
                            :icon-url="volIcon" 
                        >
                        </l-icon>
                    </l-marker>  
                
                

                <l-marker             
                   
                    :lat-lng="latLng(this.foundTarea.latitud, this.foundTarea.longitud)"                
                >
                    <l-popup :content="'Tarea: '+ this.foundTarea.nombre + '</br>' + 'Descripción: ' + this.foundTarea.descrip + '</br>' + 'Vols. requeridos: ' + this.foundTarea.cant_vol_requeridos" ></l-popup>
                    <l-icon
                        :icon-size="tareaSize"
                        :icon-url="tareaIcon"
                    >
                    </l-icon>
                </l-marker>         
      
            </l-map>
            <MapLegend></MapLegend>
        </div>   

            
        </div>

        

        


    </div>

    <div class="row pt-5">
                <h1 class="pt-5" v-if="foundVols.length != 0">Voluntarios encontrados:</h1>
                <div class="row pt-4">
                    
                        <ul v-for="(vol, index) in foundVols" :key="'volV' + index" class="list-group" id="list-tab" role="tablist">
                        <li class="list-group-item">

                            <div class="col-pt">
                                <div class="d-flex w-100 justify-content-between">                                
                                    <p class="mb-1"> {{index + 1}} - {{vol.nombre}} <br> Email: {{vol.email}}</p>                                
                                </div>
                            </div>

                            

                        </li>
                        </ul>
                   
                    </div>

                
            </div>
</div>
    
</template>

<script>
import L from 'leaflet'
import { LMap, LTileLayer, LMarker, LIcon, LPopup } from 'vue2-leaflet'

import volIcon from '../assets/vol_icon.png'
import emeIcon from '../assets/eme_icon.png'
import tareaIcon from '../assets/tarea_icon.png'
import red_dot from '../assets/red_dot.png'
import MapLegend from '../components/MapLegend'

export default {
    name: 'VolsByRadius',
    data() {
        return{
            tareas: [],
            emes: [],
            searchTarea: {},
            foundTarea: null,
            foundVols: [],
            selectedEmeId: null,
            zoom: 9,
            url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
            attribution:
                '&copy; <a href="http://osm.org/copyright">OpenStre,etMap</a> contributors',
            center: L.latLng(-33.561995, -70.562439),
            volSize: [8,8],
            emeSize: [15, 15],
            tareaSize: [18, 18],
            redDotSize: [35, 35],
            volIcon: volIcon,
            emeIcon: emeIcon, 
            redDot: red_dot,           
            tareaIcon: tareaIcon,
            currLatLng: L.latLng(-33.561995, -70.562439),
        }
    },

     components: {
        LMap,
        LTileLayer,
        LMarker,
        LIcon,
        LPopup,
        MapLegend
    },
    

    methods: {

        latLng: function(lat, lng) {
          return L.latLng(lat, lng);
        },
        onClick: function(e){
          this.currLatLng = e.latlng;
        },

        getTareas:async function(id_eme){
            try{
                let response = await this.$http.get(`/tareas/emergencia/${id_eme}`);
                this.tareas = response.data;
                console.log(response);
            }catch(error){
                console.log('error', error);
            }
        },
        
        getEmes: async function(){
            try {
                let response = await this.$http.get('/emergencias');
                this.emes  = response.data;
                console.log(response)
            } catch (error) {
                console.log('error', error);
            }
      },

      getTarea: async function(){
          try {
                let response = await this.$http.get(`/tareas/${this.searchTarea.id}`);   
                this.foundTarea = response.data;            
                console.log(response)
                
            } catch (error) {                
                console.log('error', error);
                
            }
      },      

        getVols: async function(){
          try{
              this.getTarea();
              let response = await this.$http.get(`/voluntarios/${this.searchTarea.id}/${this.searchTarea.radio}`);
              this.foundVols = response.data;
              console.log(response);        
          }catch(error){
              console.log('error', error);

          }
      }

    },

    watch:{
        selectedEmeId: function(){
            if(this.selectedEmeId != null){
                this.getTareas(this.selectedEmeId);
            }
        }

    },

    created:function(){
        this.getEmes();
        if(this.selectedEmeId != null){
            this.getTareas(this.selectedEmeId);
        }
    }
    

    
    
}
</script>

<style scoped>

.map {
    position: relative;
    height: 500px;
    width: 800px;
}
#map-eme{
    border: 2px;
    border-color: aqua;
}

</style>