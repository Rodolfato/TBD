package cl.tbd.ejemplo1.repositories;

import cl.tbd.ejemplo1.models.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

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
}
