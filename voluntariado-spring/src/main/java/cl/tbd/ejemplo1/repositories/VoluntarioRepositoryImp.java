package cl.tbd.ejemplo1.repositories;

import cl.tbd.ejemplo1.models.Tarea;
import cl.tbd.ejemplo1.models.Voluntario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Map;

@Repository
public class VoluntarioRepositoryImp implements VoluntarioRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public int countVoluntarios() {
        int total = 0;
        try(Connection conn = sql2o.open()){
            total = conn.createQuery("SELECT COUNT(*) FROM voluntario").executeScalar(Integer.class);
        }
        return total;
    }

    @Override
    public List<Voluntario> getAllVoluntarios() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT id, nombre, st_x(st_astext(location)) AS longitud, st_y(st_astext(location)) AS latitud, email, sexo FROM voluntario")
                    .executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Voluntario createVoluntario(Voluntario voluntario) {
        try(Connection conn = sql2o.open()){
            String query = "INSERT INTO voluntario (nombre, location, email, sexo) values (:vNombre, ST_GeomFromText(:vPoint, 4326), :vEmail, :vSexo)";
            String point = "POINT(" + voluntario.getLongitud() + " " + voluntario.getLatitud() + ")";
            long insertedId = (long) conn.createQuery(query, true)
                .addParameter("vNombre", voluntario.getNombre())
                .addParameter("vPoint", point)
                .addParameter("vEmail", voluntario.getEmail())
                .addParameter("vSexo", voluntario.getSexo())
                .executeUpdate().getKey();
            voluntario.setId(insertedId);
            return voluntario;        
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    @Override
    public Voluntario updateVoluntario(Voluntario voluntario, long id) {
        try(Connection conn = sql2o.open()){
            String query = "UPDATE voluntario SET nombre = :vNombre, location = ST_GeomFromText(:vPoint, 4326), email = :vEmail, sexo = :vSexo WHERE id = :updateId";
            String point = "POINT(" + voluntario.getLongitud() + " " + voluntario.getLatitud() + ")";
            conn.createQuery(query, true)
                .addParameter("updateId", id)
                .addParameter("vNombre", voluntario.getNombre())
                .addParameter("vPoint", point)
                .addParameter("vEmail", voluntario.getEmail())
                .addParameter("vSexo", voluntario.getSexo())
                .executeUpdate();
            voluntario.setId(id);
            return voluntario;        
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    @Override
    public List<Voluntario> deleteVoluntario(long id) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM voluntario WHERE id = :deleteId")
                .addParameter("deleteId", id)
                .executeUpdate();
            return getAllVoluntarios();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Map<String,Object>> getWithinRadius(long id, double radius) {
        try(Connection conn = sql2o.open()){
            List<Tarea> tarea = conn.createQuery("SELECT st_x(st_astext(location)) AS longitud, st_y(st_astext(location)) AS latitud FROM tarea WHERE id = :tId")
                .addParameter("tId", id)
                .executeAndFetch(Tarea.class);
            String point = "POINT(" + tarea.get(0).getLongitud() + " " + tarea.get(0).getLatitud() + ")";
            //Distancia de ST_Distance es en metros, se divide por 1000 en la query para obtener la distancia en kilometros
            return conn.createQuery("SELECT id, nombre, st_x(st_astext(location)) AS longitud, st_y(st_astext(location)) AS latitud, email, sexo, distancia_kms FROM (SELECT *, (ST_Distance(ST_GeomFromText(:vPoint, 4326), location::geography) / 1000) AS distancia_kms FROM voluntario ORDER BY distancia_kms ASC) AS tabla_distancias WHERE distancia_kms <= :radius")
                .addParameter("vPoint", point)
                .addParameter("radius", radius)
                .executeAndFetchTable()
                .asList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Voluntario> getOnlyVoluntariosWithTasks(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT vol.id, vol.nombre, st_x(st_astext(vol.location)) AS longitud, st_y(st_astext(vol.location)) AS latitud, vol.email, vol.sexo FROM voluntario vol JOIN ranking ran ON vol.id = ran.id_voluntario WHERE flg_participa = 1 AND flg_invitado = 1")
                    .executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }
}


