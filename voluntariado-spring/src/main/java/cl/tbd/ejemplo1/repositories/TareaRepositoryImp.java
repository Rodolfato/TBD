package cl.tbd.ejemplo1.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import cl.tbd.ejemplo1.models.Tarea;

import java.util.List;

@Repository
public class TareaRepositoryImp implements TareaRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Tarea> getAllTareas() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT id, nombre, descrip, cant_vol_requeridos, cant_vol_inscritos, id_emergencia, finicio, ffin, id_estado, st_x(st_astext(location)) AS longitud, st_y(st_astext(location)) AS latitud FROM tarea")
                    .executeAndFetch(Tarea.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Tarea createTarea(Tarea tarea) {
        try(Connection conn = sql2o.open()){
            String query = "INSERT INTO tarea (nombre, descrip, cant_vol_requeridos, cant_vol_inscritos, id_emergencia, finicio, ffin, id_estado, location) values (:tNombre, :tDescrip, :tRequeridos, :tInscritos, :idEme, :tInicio, :tFin, :idEst, ST_GeomFromText(:tPoint, 4326))";
            String point = "POINT(" + tarea.getLongitud() + " " + tarea.getLatitud() + ")";
            long insertedId = (long) conn.createQuery(query, true)
                    .addParameter("tNombre", tarea.getNombre())
                    .addParameter("tDescrip", tarea.getDescrip())
                    .addParameter("tRequeridos", tarea.getCant_vol_requeridos())
                    .addParameter("tInscritos", tarea.getCant_vol_inscritos())
                    .addParameter("idEme", tarea.getId_emergencia())
                    .addParameter("tInicio", tarea.getFinicio())
                    .addParameter("tFin", tarea.getFfin())                    
                    .addParameter("idEst", tarea.getId_estado())
                    .addParameter("tPoint", point)
                    .executeUpdate().getKey();
                    tarea.setId(insertedId);
            return tarea;        
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }        
    }

    @Override
    public Tarea updateTarea(Tarea tarea, long id) {
        try(Connection conn = sql2o.open()){
            String query = "UPDATE tarea SET nombre = :tNombre, descrip = :tDescrip, cant_vol_requeridos = :tRequeridos, cant_vol_inscritos = :tInscritos, id_emergencia = :idEme, finicio = :tInicio, ffin = :tFin, id_estado = :idEst, location = ST_GeomFromText(:tPoint, 4326) WHERE id = :updateId";
            String point = "POINT(" + tarea.getLongitud() + " " + tarea.getLatitud() + ")";
            conn.createQuery(query)
                    .addParameter("updateId", id)
                    .addParameter("tNombre", tarea.getNombre())
                    .addParameter("tDescrip", tarea.getDescrip())
                    .addParameter("tRequeridos", tarea.getCant_vol_requeridos())
                    .addParameter("tInscritos", tarea.getCant_vol_inscritos())
                    .addParameter("idEme", tarea.getId_emergencia())
                    .addParameter("tInicio", tarea.getFinicio())
                    .addParameter("tFin", tarea.getFfin())                    
                    .addParameter("idEst", tarea.getId_estado())
                    .addParameter("tPoint", point)
                    .executeUpdate();
                    tarea.setId(id);
            return tarea;        
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }        
    }

    @Override
    public List<Tarea> deleteTarea(long id) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM tarea WHERE id = :deleteId")
                .addParameter("deleteId", id)
                .executeUpdate();
            return getAllTareas();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Tarea> getTareasByEmergencia(long id_emergencia){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT id, nombre, descrip, cant_vol_requeridos, cant_vol_inscritos, id_emergencia, finicio, ffin, id_estado, st_x(st_astext(location)) AS longitud, st_y(st_astext(location)) AS latitud FROM tarea WHERE id_emergencia = :id_eme")
                    .addParameter("id_eme", id_emergencia)
                    .executeAndFetch(Tarea.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public Tarea getTarea(long id){

        try(Connection conn = sql2o.open()){
            return conn.createQuery(
                    "SELECT id, nombre, descrip, cant_vol_requeridos, cant_vol_inscritos, id_emergencia, finicio, ffin, id_estado, st_x(st_astext(location)) AS longitud, st_y(st_astext(location)) AS latitud FROM tarea WHERE id = :id_tarea")
                    .addParameter("id_tarea", id)
                    .executeAndFetchFirst(Tarea.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
    
}
