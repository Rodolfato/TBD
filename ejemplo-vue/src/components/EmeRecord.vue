<template>
    <div>
        <h1 class="text-center pt-4">Historial de emergencias</h1>
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
                        
                    <h5 v-if ="tareas.length != 0 && selectedVolId != null" class="text-center">Tareas finalizadas:</h5>
                    <h5 v-if ="tareas.length == 0 && selectedVolId != null" class="text-center">No tiene tareas finalizadas en esta emergencia.</h5>


                        <li class="list-group-item" v-for="(tarea, index) in tareas" :key="'tareaFV' + index">                        
                            <h5>{{ tarea.nombre }}</h5>
                            <p>Descripción: {{tarea.descrip}}<br>Estado: {{taskStates[tarea.id_estado]}}</p>                        
                        </li>

                    <h5 v-if ="tareas3.length != 0 && selectedVolId != null" class="text-center">Tareas canceladas:</h5>
                    <h5 v-if ="tareas3.length == 0 && selectedVolId != null" class="text-center">No tiene tareas canceladas en esta emergencia.</h5>

                        <li class="list-group-item" v-for="(tarea3, index) in tareas3" :key="'tareaFV' + index">                        
                            <h5>{{ tarea3.nombre }}</h5>
                            <p>Descripción: {{tarea3.descrip}}<br>Estado: {{taskStates[tarea3.id_estado]}}</p>                        
                        </li>
                   
                </ul>
            </div>
        </div>






    </div>
</template>

<script>
export default {
    name: 'Historial',
    data() {
        return{
            activeVols:[],
            selectedVolId: null,
            volEmes: [],
            selectedEmeId: null,
            tareas: [],
            tareas3: [],
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
                let response = await this.$http.get(`/tareas/emergencia/${id_eme}/0`);
                this.tareas  = response.data;

                let response3 = await this.$http.get(`/tareas/emergencia/${id_eme}/3`);  
                this.tareas3  = response3.data;

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