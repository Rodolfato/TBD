<template>
<div class="container-fluid">
    <h1 class="text-center pt-4">Notificar una emergencia</h1>
    
    <div class="row container-fluid pt-4">
        
        
        <!-- tratar de usar mejor los componentes para organizar el codigo y vuex para transferir datos de un comp a otro -->
        <!-- componente NewEmergencia.vue -->
        <div class="col-lg container-fluid">
            
            <form class=" pt-4">
                <div class="form-group">
                    <label for="name">Nombre</label>
                    <input placeholder="Eliga un nombre para la emergencia" class="form-control" type="text" id="nombre" v-model="newEmergencia.nombre">
                </div>

                <div class="form-group">
                    <label for="descrip">Descripción</label>
                    <textarea placeholder="Describa la emergencia" class="form-control" rows="5" type="text" id="descrip" v-model="newEmergencia.descrip"></textarea>
                </div>

                <div class="form-group">
                    <label for="finicio">Fecha de inicio</label>
                    <input class="form-control" type="date" id="finicio" v-model="newEmergencia.finicio">
                </div>

                <div class="form-group">
                    <label for="ffin">Fecha de termino</label>
                    <input class="form-control" type="date" id="ffin" v-model="newEmergencia.ffin">
                </div>

                <div class="form-group">
                    <label for="id_institucion">Elegir la institución encargada</label>
                    <select class="form-control" id="id_institucion" v-model="newEmergencia.id_institucion">                        
                        <option v-bind:value="institution.id" v-for="(institution, index) in insts" :key="index">{{institution.nombre}}</option>                        
                    </select>
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
                    <button id="but-sub" type="button" class="btn btn-lg btn-primary" @click="send">Crear emergencia</button>
                </div>

                <div class="container-fluid">
                    <button v-if="seen" id="but-sub" type="button" class="btn btn-lg btn-block btn-danger" role="button" aria-pressed="true">{{message}}</button>
                </div>
            </form>
        </div>


        <!-- componente Map.vue -->
        <div id="map-eme" class="col-lg map container-fluid">
            <div class="form-check">
                <input type="checkbox" class="form-check-input" id="mostrarVols" value="checked" v-model="showVols">
                <label class="form-check-label" for="mostrarVols">Mostrar voluntarios</label>
            </div>
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
                    v-for="(tarea, index) in tareas"
                    :lat-lng="latLng(tarea.latitud, tarea.longitud)"                
                >
                    <l-popup :content="'Tarea: '+ tarea.nombre + '</br>' + 'Descripción: ' + tarea.descrip + '</br>' + 'Vols. requeridos: ' + tarea.cant_vol_requeridos" ></l-popup>
                    <l-icon
                        :icon-size="tareaSize"
                        :icon-url="tareaIcon"
                    >
                    </l-icon>
                </l-marker>


                <l-marker
                    :key="'emeNME' + index"
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
                <class v-if="showVols">
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
                </class>        
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
  name: 'Mapa',
  data() {
    return {
      showVols: false,
      message:'',
      newEmergencia:{},
      insts:[],
      vols: [],
      emes: [],
      tareas: [],
      zoom: 9,
      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution:
        '&copy; <a href="http://osm.org/copyright">OpenStre,etMap</a> contributors',
      center: L.latLng(-33.561995, -70.562439),
      volSize: [8,8],
      emeSize: [15, 15],
      tareaSize: [18, 18],
      redDotSize: [35, 35],
      tareaIcon: tareaIcon,
      volIcon: volIcon,
      emeIcon: emeIcon,
      redDot: red_dot, 
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
            this.newEmergencia.latitud = this.currLatLng.lat;
            this.newEmergencia.longitud = this.currLatLng.lng;
            this.seen = true;
            if(!this.newEmergencia.nombre){
                this.message = 'Debe ingresar un nombre'
                return false
            }
            if(!this.newEmergencia.descrip){
                this.message = 'Debe ingresar una descripción'
                return false
            }
            if(!this.newEmergencia.finicio){
                this.message = 'Debe ingresar una fecha de inicio'
                return false
            }
            if(!this.newEmergencia.ffin){
                this.message = 'Debe ingresar una fecha de termino'
                return false
            }
            if(this.newEmergencia.id_institucion == null){
                this.message = 'Debe ingresar una institución encargada de la emergencia'
                return false
            }
            try {
                var result = await this.$http.post('/emergencias', this.newEmergencia);
                let emergencia = result.data;
                //this.message = `Se creó una nueva emergencia con id: ${emergencia.id}`;
                this.message = `Emergencia "${emergencia.nombre}" creada correctamente.`;
                this.newEmergencia = {};
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
        },
        getTareas:async function(){
            try{
                let response = await this.$http.get('/tareas');
                this.tareas = response.data;
                console.log(response);
            }catch(error){
                console.log('error', error);
            }
        }
  },

  created:function(){
          this.getVols();
          this.getEmes();
          this.getInsts();
          this.getTareas();
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