import time
import psycopg2
import json
import random
import time 
import datetime

current_milli_time = lambda: int(round(time.time() * 1000))

#formato AAAA-MM-DD
def rand_date(iniDate, endDate):

  initstp = time.mktime(datetime.datetime.strptime(iniDate, "%Y-%m-%d").timetuple())
  endtstp = time.mktime(datetime.datetime.strptime(endDate, "%Y-%m-%d").timetuple())
  newDate = random.randrange( initstp, endtstp )
  newDateTimestamp = datetime.datetime.fromtimestamp(newDate)
  
  return newDateTimestamp.strftime("%Y-%m-%d")

def get_connection_db(dbName):
    return psycopg2.connect(user="postgres", password="postgres", host="127.0.0.1", port="5432", database=dbName)

def fill_voluntario(quantity, dbName):

    names = ["Ana", "Enzo", "Hugo", "Juan", "Lara", "Leo", "Luz", "Sara", "Hector", "Helena", "Helena", "Lucia", "Manuel", "Mariana", "Martin", "Martin", "Isabel", "Lucia", "Marta", "Moises", "Raquel", "Samuel", "Alberto", "Felipe", "Beatriz", "Daniela", "Claudia", "Laura", "Daniel", "Pablo", "Alvaro", "Mateo", "Matias", "Diego", "David", "Sergio", "Marcos", "Carlos", "Miguel", "Lucas", "Mario", "Valentina"]

    last_names = ["Gonzalez", "Rodriguez", "Gomez", "Lopez", "Fernandez", "Romero", "Sanchez", "Garcia", "Perez", "Martinez", "Diaz", "Sosa", "Torres", "Alvarez", "Ruiz", "Ramirez", "Acosta", "Suarez", "Gutierrez", "Aguire", "Montes", "Gimenez", "Rojas", "Pereyra", "Molina", "Castro", "Ortiz", "Silva", "Juarez", "Ojeda", "Ponce", "Arias", "Figueroa", "Ramos", "Correa", "Hernandez", "Escobar", "Mendoza", "Caroca", "Muena", "Campos", "Soto"]

    try:	
        connection = get_connection_db(dbName)
        cursor = connection.cursor()

        for i in range(0, quantity):
            full_name = random.choice(names) + " " + random.choice(last_names)
            birthday = rand_date("1970-01-01", "2002-01-01")
            cursor.execute("INSERT INTO voluntario (nombre, fnacimiento) VALUES (%s, %s)",
                            ( full_name, birthday ) )

        connection.commit()
        print("Se agregaron " + str(quantity) + " registros en la tabla voluntario")     


    except (Exception, psycopg2.Error) as error :
        if(connection):
            print("Error en la tabla voluntario:")
            print("\t" + str(error))
      

    finally:
        #closing database connection.
        if(connection):
            cursor.close()
            connection.close()
            print("PostgreSQL connection is closed")

def fill_institucion(dbName):

    names = ["Techo", "Cruz Roja", "Voluntariado 10 de Octubre", "CANEC", "Trabajo en la Calle", "YMCA", "Greenpeace", "America Solidaria", "Hogar de Cristo", "COANIL", "COANIQUEM"]

    try:	
        connection = get_connection_db(dbName)
        cursor = connection.cursor()

        for i in range(0, len(names)):
            name = names[i]
            
            cursor.execute("INSERT INTO institucion (nombre, descrip) VALUES (%s, %s)",
                            ( name, name ) )

        connection.commit()
        print("Se agregaron " + str(len(names)) + " registros en la tabla institucion")   


    except (Exception, psycopg2.Error) as error :
        if(connection):
            print("Error en la tabla institucion:")
            print("\t" + str(error))           
      

    finally:
        #closing database connection.
        if(connection):
            cursor.close()
            connection.close()
            print("PostgreSQL connection is closed")


def fill_habilidad(dbName):
    
    descriptions = ["Ser bueno con ninos", "Ser bueno con animales", "Tener fuerza", "Tiempo para largas jornadas", "Paciencia con tercera edad", "Buen estado fisico", "Capacidad para educar", "Capacidad para entretener"]
    
    try:	
        connection = get_connection_db(dbName)
        cursor = connection.cursor()

        for i in range(0, len(descriptions)):
            description = descriptions[i]
            cursor.execute("INSERT INTO habilidad (descrip) VALUES (%s)",
                            (description, ) )

        connection.commit()
        print("Se agregaron " + str(len(descriptions)) + " registros en la tabla habilidad")   


    except (Exception, psycopg2.Error) as error :
        if(connection):
            print("Error en el ingreso de datos a tabla habilidad: ")
            print("\t" + str(error))         

    finally:
        #closing database connection.
        if(connection):
            cursor.close()
            connection.close()
            print("PostgreSQL connection is closed")

def fill_emergencia(dbName):

    ids = []

    names = ["Incendio", "Terremoto", "Falta de personal", "Derrumbe", "Aluvion"]
    descriptions = ["Incendio que afecto a varias casa en comuna de Santiago", "Fuerte terremoto afecto a octava region", "Se necesitan voluntarios para atender casa de ancianos", "Remota localidad cordillerana fue afectada por un derrumbe", "Rio Las Minas desbordado"]
    
    try:	
        connection = get_connection_db(dbName)
        cursor = connection.cursor()

        cursor.execute("SELECT * FROM institucion")
        for record in cursor:
            ids.append(record[0])
        
        for i in range(0, len(names)):
           
            name = names[i]
            description = descriptions[i]
            fstart = rand_date("1970-01-01", "2002-01-01")
            fend = rand_date(fstart, "2002-05-05")
            idinst = random.choice(ids)
            cursor.execute("INSERT INTO emergencia (nombre, descrip, finicio, ffin, id_institucion) VALUES (%s, %s, %s, %s, %s)",
                            (name, description, fstart, fend, idinst) )       

        connection.commit()
        print("Se agregaron " + str(len(names)) + " registros en la tabla emergencia")   


    except (Exception, psycopg2.Error) as error :
        if(connection):
            print("Error en el ingreso de datos a tabla emergencia: ")
            print("\t" + str(error))         

    finally:
        #closing database connection.
        if(connection):
            cursor.close()
            connection.close()
            print("PostgreSQL connection is closed")

def get_data(table, dbName):
    obj_ids = []

    connection = get_connection_db(dbName)
    cursor = connection.cursor()

    cursor.execute("SELECT * FROM " + table)
    for record in cursor:
        obj_ids.append(record[0])
    
    return obj_ids


def fill_eme_habilidad(quantity, dbName):

    ids_hab = get_data("habilidad", dbName)
    ids_eme = get_data("emergencia", dbName)

    print(ids_hab)
    print(ids_eme)

    try:	
        connection = get_connection_db(dbName)
        cursor = connection.cursor()

        for i in range(0, quantity):
            id_h = random.choice(ids_hab)
            id_e = random.choice(ids_eme)

            cursor.execute("INSERT INTO eme_habilidad (id_emergencia, id_habilidad) VALUES (%s, %s)", 
                            (id_e, id_h) )

        connection.commit()
        print("Se agregaron " + str(quantity) + " registros en la tabla emergencia")   


    except (Exception, psycopg2.Error) as error :
        if(connection):
            print("Error en el ingreso de datos a tabla emergencia: ")
            print("\t" + str(error))         

    finally:
        #closing database connection.
        if(connection):
            cursor.close()
            connection.close()
            print("PostgreSQL connection is closed")
    


dbName_f = "postgres"
fill_voluntario(100, dbName_f)
fill_institucion(dbName_f)
fill_habilidad(dbName_f)
fill_emergencia(dbName_f)
fill_eme_habilidad(15, dbName_f)