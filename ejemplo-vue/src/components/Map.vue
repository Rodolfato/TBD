<template>
<div>
    <!-- tratar de usar mejor los componentes para organizar el codigo y vuex para transferir datos de un comp a otro -->
    <!-- componente NewEmergencia.vue -->
    <div class="container">
        <h1>Agregar una emergencia</h1>
        <form>
            <div>
                <label for="name">Nombre</label>
            <input type="text" id="nombre" v-model="newEmergencia.nombre">
            </div>

            <div>
                <label for="descrip">Descripción</label>
            <input type="text" id="descrip" v-model="newEmergencia.descrip">
            </div>

            <div>
                <label for="finicio">Fecha de inicio</label>
            <input type="date" id="finicio" v-model="newEmergencia.finicio">
            </div>

            <div>
                <label for="ffin">Fecha de termino</label>
            <input type="date" id="ffin" v-model="newEmergencia.ffin">
            </div>

            <div>
                <label for="id_institucion">Elegir la institución encargada</label>
            <select id="id_institucion" v-model="newEmergencia.id_institucion">
                <option v-bind:value="institution.id" v-for="(institution, index) in insts" :key="index">{{institution.nombre}}</option>
            </select>
            </div>
            <div>
                <label for="latitud">Latitud</label>
            <input :placeholder="currLatLng.lat.toFixed(6)" :readonly="currLatLng.lat" id="latitud">
            </div>
            <div>
                <label for="longitud">Longitud</label>
            <input :placeholder="currLatLng.lng.toFixed(6)" :readonly="currLatLng.lng" id="longitud">
            </div>
            
            <div>
                <button type="button" @click="send">Crear</button>
            </div>
            <div class="info">
                <p class="message">
                    {{message}}
                </p>
            </div>
        </form>
    </div>


    <!-- componente Map.vue -->
    <div class="map">
        <l-map :zoom="zoom" :center="center" @click="onClick">
            <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
            <l-marker
                :key="index"
                v-for="(eme, index) in emes"
                :lat-lng="latLng(eme.latitud, eme.longitud)"                
            >
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
    </div>



</div>
</template>

<script>

import L from 'leaflet'
import { LMap, LTileLayer, LMarker, LIcon, LPopup } from 'vue2-leaflet'

import volIcon from '../assets/vol_icon.png'
import emeIcon from '../assets/eme_icon.png'

export default {
  name: 'Mapa',
  data() {
    return {
      message:'',
      newEmergencia:{},
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
      volIcon: volIcon,
      emeIcon: emeIcon,
      currLatLng: L.latLng(-33.561995, -70.562439)
    };
  },
  components: {
    LMap,
    LTileLayer,
    LMarker,
    LIcon,
    LPopup
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
            if(!this.newEmergencia.id_institucion){
                this.message = 'Debe ingresar una institución encargada de la emergencia'
                return false
            }
            try {
                var result = await this.$http.post('/emergencias', this.newEmergencia);
                let emergencia = result.data;
                this.message = `Se creó una nueva emergencia con id: ${emergencia.id}`;
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
    height: 500px;
    width: 300px;
}
</style>