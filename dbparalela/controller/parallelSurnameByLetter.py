from flask import jsonify,request, make_response
from common import *
from controller import app, general
from model import *

@app.route('/api/parallelSurnameByLetter/voluntariosPorInicial/<inicial>')
def select_voluntariosPorInicial(inicial):
    if myId == primaryId:
        data = request.json
        urlBase = "http://ADDR/api/parallelSurnameByLetter/slave/voluntariosPorInicial/" + inicial
        responses = general.send_to_all("requests.get",servers,nbrServers,urlBase,data) #Nodo principal manda peticion a los demas servidores/nodos 
        print(responses)

        return {"resp":general.union_responses_list(responses)}
    else:
        return "deje ejecutar en el nodo primary"


@app.route('/api/parallelSurnameByLetter/slave/voluntariosPorInicial/<inicial>',methods=['GET'])
def select_slaveVoluntariosPorInicial(inicial):
    resp = select_data("SELECT nombre FROM"
    +" (SELECT nombre, SUBSTRING(nombre, POSITION(' ' in nombre) + 1, 1) AS inicial FROM voluntario) AS sub" 
    +" WHERE sub.inicial = '" + inicial + "'",dbConnConfig)

    print (["select_slaveVoluntariosPorInicial",myId,resp])
    return {"resp" : resp, "server":myId }
