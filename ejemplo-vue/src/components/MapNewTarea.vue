<template>
<div class="container-fluid">
    <h1 class="text-center pt-4">Crear una tarea</h1>
    
    <div class="row container-fluid pt-4">
        
        
        <!-- tratar de usar mejor los componentes para organizar el codigo y vuex para transferir datos de un comp a otro -->
        <!-- componente NewEmergencia.vue -->
        <div class="col-lg container-fluid">
            
            <form class=" pt-4">
                <div class="form-group">
                    <label for="name">Nombre</label>
                    <input placeholder="Eliga un nombre para la tarea" class="form-control" type="text" id="nombre" v-model="newTarea.nombre">
                </div>

                <div class="form-group">
                    <label for="descrip">Descripción</label>
                    <textarea placeholder="Describa la tarea" class="form-control" rows="5" type="text" id="descrip" v-model="newTarea.descrip"></textarea>
                </div>

                <div class="form-group">
                    <label for="finicio">Fecha de inicio</label>
                    <input class="form-control" type="date" id="finicio" v-model="newTarea.finicio">
                </div>

                <div class="form-group">
                    <label for="ffin">Fecha de termino</label>
                    <input class="form-control" type="date" id="ffin" v-model="newTarea.ffin">
                </div>

                <div class="form-group">
                    <label for="id_institucion">Elegir la emergencia</label>
                    <select class="form-control" id="id_emergencia" v-model="newTarea.id_emergencia">                        
                        <option v-bind:value="emergencia.id" v-for="(emergencia, index) in emes" :key="index">{{emergencia.nombre}}</option>                        
                    </select>
                </div>

                <div>
                    <label for="cant_vol_requeridos">Cantidad de voluntarios requeridos</label>
                    <input class="form-control" type="number" id="cant_vol_requeridos" v-model="newTarea.cant_vol_requeridos">
                </div>

                <div class="form-group">
                    <label for="latitud">Latitud</label>
                <input class="form-control" :placeholder="currLatLng.lat.toFixed(6)" :readonly="currLatLng.lat" id="latitud">
                </div>

                <div class="form-group">
                    <label for="longitud">Longitud</label>
                <input class="form-control" :placeholder="currLatLng.lng.toFixed(6)" :readonly="currLatLng.lng" id="longitud">
                </div>
                
                <div class="form-group text-center">
                    <button id="but-sub" type="button" class="btn btn-lg btn-primary" @click="send">Crear tarea</button>
                </div>

                <div class="container-fluid">
                    <button v-if="seen" id="but-sub" type="button" class="btn btn-lg btn-block btn-danger" role="button" aria-pressed="true">{{message}}</button>
                </div>
            </form>
        </div>


        <!-- componente Map.vue -->
        <div id="map-eme" class="col-lg map container-fluid">
            <l-map :zoom="zoom" :center="center" @click="onClick">
                <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>

                <l-marker        
                    :lat-lng="latLng(currLatLng.lat, currLatLng.lng)"                
                >    
                    <l-popup :content="'Latitud: '+ currLatLng.lat.toFixed(6) + '</br>' + 'Longitud: ' + currLatLng.lng.toFixed(6)"></l-popup>                
                    <l-icon
                        :icon-size="redDotSize"
                        :icon-url="redDot"
                    >
                    </l-icon>
                </l-marker> 


                <l-marker
                    :key="index"
                    v-for="(eme, index) in emes"
                    :lat-lng="latLng(eme.latitud, eme.longitud)"                
                >
                    <l-popup :content="'Emergencia: '+ eme.nombre + '</br>' + 'Descripción: ' + eme.descrip"></l-popup>
                    <l-icon
                        :icon-size="emeSize"
                        :icon-url="emeIcon"
                    >
                    </l-icon>
                </l-marker>  
                <l-marker
                    :key="index"
                    v-for="(vol, index) in vols"
                    :lat-lng="latLng(vol.latitud, vol.longitud)"
                >
                    <l-popup :content="'Voluntario: '+ vol.nombre + '</br>' + 'E-mail: ' + vol.email + '</br>' + 'Género: ' + vol.sexo"></l-popup>
                    <l-icon
                        :icon-size="volSize"
                        :icon-url="volIcon" 
                    >
                    </l-icon>
                </l-marker>
                        
            </l-map>
            <MapLegend></MapLegend>
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
  name: 'MapaNewTarea',
  data() {
    return {
      message:'',
      newTarea:{},
      insts:[],
      vols: [],
      emes: [],
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
      seen: false
    };
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

      getVols: async function(){
            try {
                let response = await this.$http.get('/voluntarios');
                this.vols  = response.data;
                console.log(response)
            } catch (error) {
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
      onClick: function(e){
          this.currLatLng = e.latlng;
      },
      send:async function(){
            this.message = '';
            this.newTarea.latitud = this.currLatLng.lat;
            this.newTarea.longitud = this.currLatLng.lng;
            this.seen = true;
            if(!this.newTarea.nombre){
                this.message = 'Debe ingresar un nombre'
                return false
            }
            if(!this.newTarea.descrip){
                this.message = 'Debe ingresar una descripción'
                return false
            }
            if(!this.newTarea.finicio){
                this.message = 'Debe ingresar una fecha de inicio'
                return false
            }
            if(!this.newTarea.ffin){
                this.message = 'Debe ingresar una fecha de termino'
                return false
            }
            if(this.newTarea.id_emergencia == null){
                this.message = 'Debe ingresar una emergencia para la tarea'
                return false
            }
            if(this.newTarea.cant_vol_requeridos == null || this.newTarea.cant_vol_requeridos <= 0){
                this.message = 'Debe ingresar una cantidad de voluntarios mayor o igual a cero'
                return false

            }
            
            try {
                this.newTarea.cant_vol_inscritos = 0;
                var result = await this.$http.post('/tareas', this.newTarea);
                let tarea = result.data;
                //this.message = `Se creó una nueva emergencia con id: ${emergencia.id}`;
                this.message = `Tarea "${tarea.nombre}" creada correctamente.`;
                this.newTarea = {};
                this.getEmes();
            } catch (error) {
                console.log('error', error)
                this.message = 'Ocurrió un error'
            }           
            
        },
        getInsts:async function(){
            try {
                let response = await this.$http.get('/instituciones');
                this.insts  = response.data;
                console.log(response)
            } catch (error) {
                console.log('error', error);
            }
        }
  },

  created:function(){
          this.getVols();
          this.getEmes();
          this.getInsts();
      }
};

</script>

<style scoped>
.map {
    position: relative;
    height: 800px;
    width: 500px;
}
#map-eme{
    border: 2px;
    border-color: aqua;
}
</style>