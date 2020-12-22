<template>
    <div id="homepage" class="container-fluid m-0 p-0">

        <div id="first" class="full-height p-4">
        <div class="container pt-5">

            <div class="row pb-5">

                <div class="col-lg-6 pt-4 my-auto">
                    <img id="logo" src="../assets/logo_fullres.png" class="img-fluid">
                    
                </div>

                <div class="col-lg-6">
                    <div class="text-center mb-3 pt-4 my-auto bg-transparent">
                        <h1 id="title" class="display-5">Voluntariado Comparte</h1>
                        <h3>Compartiendo nuevas oportunidades</h3>
                        <h3 id="selection">Ingresar como:</h3> 
                    </div>
                    <div class="row">
                        <div class="col-lg-6">
                            <button id="button" type="button" class="btn btn-primary btn-sm" @click="setPermission('admin')">Administrador</button>
                        </div>
                        <div class="col-lg-6">
                            <button id="button" type="button" class="btn btn-primary btn-sm" @click="setPermission('vol')">Voluntario</button>
                        </div>
                    </div>
                </div>                    
            </div>
        </div>       
    </div>

    <div id="second" class="bg-light p-3 p-md-5"> 
        <div class="row">
            <div class="col-lg">
                <img id="sabout" src="../assets/acogida_fullres.jpg" class="rounded-circle img-fluid" >
                <h4 class="font-weight-bold">Conectando personas</h4>
                    <h5 class="ml-md-4 mr-md-4 text-justify">
                        Como fundación una de nuestras más importantes labores es conectar personas afectadas por desastres naturales
                        y distintos tipos de emergencia para el rápido y eficiente movimiento de ayuda.
                    </h5>
            </div>
            <div class="col-lg">
                 <img id="sabout" src="../assets/ayuda_humanitaria.jpeg" class="rounded-circle img-fluid" >
                <h4 class="font-weight-bold">Ayuda humanitaria</h4>
                <h5 class="ml-md-4 mr-md-4 text-justify">
                        Mediante el rápido contacto y movimiento de personal voluntario con variadas habilidades y especialidades, 
                        nuestro objetivo principal es la eficaz protección de todos aquellos afectados por emergencias de diversos tipos
                        sin importar cuál sea su necesidad.
                    </h5>
            </div>
            <div class="col-lg">
                <img id="sabout" src="../assets/reconstruccion.jpg" class="rounded-circle img-fluid" >
                <h4 class="font-weight-bold">Reconstrucción</h4>
                <h5 class="ml-md-4 mr-md-4 text-justify">
                        La destrucción devastadora que dejan los desastres naturales a su paso llevan a la necesidad de un constante movimiento
                        de personal voluntario para ayudar a solventar las consecuencias después cualquier tipo de emergencia.
                    </h5>
            </div>
        </div>
    </div>

    <div id="second" class="bg-info p-3 p-md-5"> 

        <div class="row">
            <div class="col-lg">
                <img id="sabout" src="../assets/unete.jpg" class="rounded-circle img-fluid center" >
                <h4 class="font-weight-bold">¡ÚNETE!</h4>
                <h5 class="ml-md-4 mr-md-4">
                    Siempre estamos en la búsqueda de nuevos integrantes para apoyar nuestra misión.
                </h5>
            </div>

            <div class="col-lg">
                <h5 class="font-weight-bold">Queremos que seas parte de nuestro equipo</h5>
                <p>Contáctanos rellenando la siguiente forma</p>

                <form>

                    <div class="form-group text-left">

                        <input type="email" class="form-control" placeholder="Ingresa tu email">
                        <textarea class="form-control mt-3" placeholder="Cuentanos por qué quieres unirte" rows="5"></textarea>

                    </div>
                    <button id type="submit" class="btn btn-primary">Enviar mensaje</button>

                </form>
            </div>

        </div>

    </div>
    
    
        
        
    </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
    props: {
    ...mapState(['permission'])
    },
    methods: {
        setPermission: function(value){
            this.$store.state.permission = value;
        }
    }
}
</script>

<style scoped>
    .full-height{
        min-height: 85vh;
        
    }
    #first{
        background-color: #92D2F2;
        background: url("../assets/torres2.jpg") no-repeat center;  
        

    }

    #logo{
        max-height: 100vh;
        position: relative;
    }

    #second{
        min-height: 40vh;
    }

    #title{
        color:rgb(15, 99, 99);
        
    }

    #button{
        color: white;
        background-color: rgb(15, 99, 99);
        border-color: #2c3e50;
        
    }

    #selection{
        color: white;
        -webkit-text-stroke: 1.5px #2c3e50;
    }

    #sabout{
        height: 35vh;
        width: 35vh;
        padding: 10px;
    }

    #homepage{
        text-align: center;
    }
</style>