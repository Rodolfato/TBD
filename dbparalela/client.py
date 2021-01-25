import random
import requests
import time
import datetime
import names

###############################################################################
current_milli_time = lambda: int(round(time.time() * 1000))


###############################################################################
def run_http_func(http_func,action, url, data=None):
    res = eval(http_func)(url, json=data)
    if res.ok:
        print ([action,data,"ok", len(res.json()["resp"]) ])
    else:
        print ([action,data,"error",0])

###############################################################################
def delete_tablas():
    run_http_func("requests.delete","delete_voluntario", 'http://localhost:5000/api/parallelDelete/voluntario/all/',None)

###############################################################################
def get_data(cols, x):
    data={}
    genders = ["male", "female"] 
    rand_gend = random.choice(genders)
    name = names.get_full_name(gender=rand_gend)
    for key, value in cols.items():
        v=None
        
        if value["type"]=="num":
            v = x
        elif value["type"]=="txt":
            v = value["value"].replace("X",str(x))
        elif value["type"]=="name":
            v = name
        elif value["type"]=="email":
            v = value["value"].replace("X", name.replace(" ", "_").lower() + str(random.randrange(100)))
        elif value["type"]=="gnd":
            v = rand_gend[0]
        elif value["type"]=="dep": #para fk pero no esta en el scope de esta entrega dado que solo se pidieron voluntarios
            v = random.randrange(value["value"][0],value["value"][1])
        data[key]=v
    return data

###############################################################################
def create_tabla(amount,data_template,action, url):
    for i in range(amount):
        data = get_data(data_template, i)
        run_http_func("requests.post",action, url,data)


###############################################################################
def create_tablas(tables):
    create_tabla(tables["persona"],
                 {"rut":{"type":"num","value":0},
                  "nombre": {"type":"name"},
                  "email": {"type":"email","value":"X@tbd.cl"},
                  "sexo": {"type":"gnd"}
                  },
                 "insert_voluntario", 'http://localhost:5000/api/parallelInsert/voluntario/')

def main():
    delete_tablas()
    create_tablas({"persona":200})


main()

