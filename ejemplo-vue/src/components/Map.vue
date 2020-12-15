<template>
<div>
    <div class="map">
        <l-map :zoom="zoom" :center="center">
            <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
            <l-marker
                :key="index"
                v-for="(item, index) in items"
                :lat-lng="latLng(item.latitud, item.longitud)"
            >
            </l-marker>
        </l-map>
    </div>
</div>
</template>

<script>

import L from 'leaflet';
import { LMap, LTileLayer, LMarker } from 'vue2-leaflet';

export default {
  name: 'Map',
  data() {
    return {
      items: [],
      zoom: 9,
      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution:
        '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      center: L.latLng(-33.561995, -70.562439)
    };
  },
  components: {
    LMap,
    LTileLayer,
    LMarker,
  },

  methods: {
      latLng: function(lat, lng) {
          return L.latLng(lat, lng);
      },

      getData: async function(){
            try {
                let response = await this.$http.get('/voluntarios');
                this.items  = response.data;
                console.log(response)
            } catch (error) {
                console.log('error', error);
            }
      }
  },

  created:function(){
          this.getData();
      }
};

</script>

<style scoped>
.map {
    position: fixed;
    height: 500px;
    width: 500px;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
</style>