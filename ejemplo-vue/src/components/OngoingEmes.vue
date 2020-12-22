<template>
    <div>
        <h1 class="text-center pt-4">Situación actual</h1>
        <div class="container-fluid row">
            <div class="container-fluid col">          
                <form>
                    <div class="form-group">
                        <label for="id_voluntario">Elegir el voluntario</label>
                        <select class="form-control" id="id_voluntario" v-model="selectedVolId">                        
                            <option v-bind:value="vol.id" v-for="(vol, index) in activeVols" :key="'volER' + index">{{vol.id}} {{vol.nombre}}</option>                        
                        </select>
                    </div>
                </form>
            </div>   
            <div class="col"></div>
            
        </div>

        <div class="container-fluid row">

            <div class="container-fluid col">
                <ul class="list-group">
                   <a type="button" class="btn btn-primary btn-outline-primary list-group-item" v-for="(eme, index) in volEmes" :key="'emeFV' + index" @click="getTareas(eme.id)">{{eme.nombre}}</a>
                </ul>
            </div>

            <div v-if="selectedVolId != null" class="container-fluid col">

                <ul class="list-group" id="tareas">

                        <h5 v-if ="tareas1.length != 0 && selectedVolId != null" class="text-center">Tareas pendientes:</h5>
                        <h5 v-if ="tareas1.length == 0 && selectedVolId != null" class="text-center">No tiene tareas pendientes en esta emergencia.</h5>


                        <li class="list-group-item" v-for="(tarea1, index) in tareas1" :key="'tareaFV' + index">                        
                            <h5>{{ tarea1.nombre }}</h5>
                            <p>Descripción: {{tarea1.descrip}}<br>Estado: {{taskStates[tarea1.id_estado]}}</p>                        
                        </li>


                        <h5 v-if ="tareas2.length != 0 && selectedVolId != null" class="text-center">Tareas en proceso:</h5>
                        <h5 v-if ="tareas2.length == 0 && selectedVolId != null" class="text-center">No tiene tareas en proceso en esta emergencia.</h5>


                        <li class="list-group-item" v-for="(tarea2, index) in tareas2" :key="'tareaFV2' + index">                        
                            <h5>{{ tarea2.nombre }}</h5>
                            <p>Descripción: {{tarea2.descrip}}<br>Estado: {{taskStates[tarea2.id_estado]}}</p>                        
                        </li>

                        
                   
                </ul>
            </div>
        </div>






    </div>
</template>

<script>
export default {
    name: 'SitActual',
    data() {
        return{
            activeVols:[],
            selectedVolId: null,
            volEmes: [],
            selectedEmeId: null,
            tareas1: [],
            tareas2: [],
            taskStates: ['Finalizada', 'Pendiente', 'En Proceso', 'Cancelada']
        };


    },
    methods: {
        getActiveVols: async function(){
            try {
                let response = await this.$http.get('/voluntarios/onlytareas');
                this.activeVols  = response.data;
                console.log(response)
            } catch (error) {
                console.log('error', error);
            }
        },
        getTareas: async function(id_eme){
            try {
                let response = await this.$http.get(`/tareas/emergencia/${id_eme}/1`);                
                this.tareas1  = response.data;
                let response2 = await this.$http.get(`/tareas/emergencia/${id_eme}/2`);  
                this.tareas2  = response2.data;

                console.log(response)
            } catch (error) {
                console.log('error', error);
            }
        }        
    },
    created:function(){
          this.getActiveVols();
      },
    
    watch:{
        selectedVolId: async function(){
            if(this.selectedVolId != null){            
                try {
                    let response = await this.$http.get(`/emergencias/voluntario/${this.selectedVolId}`);
                    this.volEmes  = response.data;
                    console.log(response)
                } catch (error) {
                    console.log('error', error);
                }
            }
        }

    },
};

</script>

<style scoped>

</style>