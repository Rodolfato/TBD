package cl.tbd.ejemplo1.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import cl.tbd.ejemplo1.models.Tarea_habilidad;

public class Tarea_habilidadRepositoryImp implements Tarea_habilidadRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Tarea_habilidad> getAllTarea_habilidades() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM tarea_habilidad")
                    .executeAndFetch(Tarea_habilidad.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Tarea_habilidad createTarea_habilidad(Tarea_habilidad tarea_habilidad) {
        try(Connection conn = sql2o.open()){
            long insertedId = (long) conn.createQuery("INSERT INTO tarea_habilidad (id, id_emehab, id_tarea) VALUES (:th_id, :th_id_emehab, :th_id_tarea)", true)
                    .addParameter("th_id", tarea_habilidad.getId())
                    .addParameter("th_id_emehab", tarea_habilidad.getId_emehab())
                    .addParameter("th_id_tarea", tarea_habilidad.getId_tarea())
                    .executeUpdate().getKey();
                    tarea_habilidad.setId(insertedId);
            return tarea_habilidad;        
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }        
    }


    
}
