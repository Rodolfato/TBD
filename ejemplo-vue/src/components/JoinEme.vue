<template>
    <div>
        <h1 class="text-center pt-4">Inscripción de tareas</h1>
        <div class="container-fluid col-lg-4 pt-4">          
            <form>
                <div class="form-group">
                    <label for="id_voluntario">Elegir el voluntario</label>
                    <select class="form-control" id="id_voluntario" v-model="selectedVolId">                        
                        <option v-bind:value="vol.id" v-for="(vol, index) in vols" :key="'volER' + index">{{vol.id}} {{vol.nombre}}</option>                      
                    </select>
                    <label for="id_emergencia">Elegir emergencia</label>
                    <select class="form-control" id="id_emergencia" v-model="selectedEmeId" @change="getTareas($event.target.value)">
                        <option v-bind:value="eme.id" v-for="(eme, index) in emes" :key="'emeFV' + index">{{eme.id}} {{eme.nombre}}</option>
                    </select>
                    <label for="id_tarea">Elegir tarea</label>
                    <select class="form-control" id="id_tarea" v-model="selectedTaskId" @change="setRanking()">
                        <option v-bind:value="task.id" v-for="(task, index) in tasks" :key="'task' + index">{{task.id}} {{task.nombre}}</option>
                    </select>
                    <br>
                    <div class="text-center">
                        <button id="but-sub" type="button" class="btn btn-lg btn-primary" @click="postInsc">Inscribirse</button>
                    </div>
                    <h5 class="text-center pt-4">Su ranking para esta tarea es de: {{ranking}}</h5>
                </div>
                <div class="text-center" v-if="mensaje != ''"> 
                        <div class="alert alert-success">{{mensaje}}</div>
                </div>
            </form>
        </div>  
    </div>
</template>

<script>
export default {
    name: 'InscEme',
    data() {
        return {
            ranking: 0,
            vols: [],
            selectedVolId: null,
            emes: [],
            selectedEmeId: null,
            tasks: [],
            selectedTaskId: null,
            taskStates: ['Finalizada', 'Pendiente', 'En Proceso', 'Cancelada'],
            newRanking: {},
            mensaje: ""
        }
    },
    methods: {
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
        getTareas: async function(id_eme){
            try {
                let response1 = await this.$http.get(`/tareas/emergencia/${id_eme}/1`);
                let response2 = await this.$http.get(`/tareas/emergencia/${id_eme}/2`);  
                this.tasks  = Object.assign({}, response1.data, response2.data);
            } catch (error) {
                console.log('error', error);
            }
        },
        setRanking: async function(){
            try {
                let response = await this.$http.get(`/rankings/getRanking?id_tarea=${this.selectedTaskId}&id_vol=${this.selectedVolId}`)
                console.log(response);
                this.ranking = response.data;
            } catch (error) {
                console.log('error', error)
            }
        },
        postInsc: async function() {
            try {
                this.newRanking.id_voluntario = this.selectedVolId;
                this.newRanking.id_tarea = this.selectedTaskId;
                this.newRanking.puntaje = this.ranking;
                this.newRanking.flg_invitado = 2;
                this.newRanking.flg_participa = 1;
                var result = await this.$http.post('/rankings', this.newRanking);
                this.newRanking = {};
                let ran = result.data;
                this.mensaje = `Ranking nro. "${ran.id}" creado correctamente.`;
            } catch (error) {
                console.log('error', error)
                this.mensaje = 'Ocurrió un error'
            }  
        }

    },
    created: function(){
        this.getVols();
        this.getEmes();
    }
}
</script>

<style scoped>

</style>