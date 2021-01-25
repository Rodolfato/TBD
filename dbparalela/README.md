#Crear 3 bases de datos llamadas voluntario0, voluntario1 y voluntario2, y restaurar usando el backup db_paralelas_voluntario

#Instalaciones de módulos para Python 2 (Testeado con Python 2.7.18)
```sh
pip install flask
sudo pip install psycopg2==2.7.5 --ignore-installed
pip install psycopg2-binary
pip install requests
pip install names
```
#Inicialización del proyecto
##Primero se matan los procesos que estén utilizando los puertos 5000, 5001 y 5002
```sh
sudo kill -9 `sudo lsof -t -i:5000`
sudo kill -9 `sudo lsof -t -i:5001`
sudo kill -9 `sudo lsof -t -i:5002`
```

##Luego ejecutamos tres instancias paralelas de main.py con distintos archivos de configuración cuyos nombres serán ingresados como variable de entorno
```sh
python main.py config/config0.json & python main.py config/config1.json & python main.py config/config2.json
```

##En otra terminal ejecutamos client.py para vaciar/llenar las bases de datos con los datos respectivos a los voluntarios
```sh
python client.py
```

##Una vez llenadas las tablas de las bases de datos haremos uso de nuestro servicio REST usando curl o un buscador web con la URL que se muestra a continuación,
## reemplazando "{letra inicial del apellido paterno}" con la letra deseada, por ejemplo, C... (puerto 5000 escucha al nodo primario)
## URL de ejemplo: http://localhost:5000/api/parallelSurnameByLetter/voluntariosPorInicial/C 
```sh
curl http://localhost:5000/api/parallelSurnameByLetter/voluntariosPorInicial/{letra inicial del apellido paterno}
```
