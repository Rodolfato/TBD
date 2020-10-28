package cl.tbd.proyectotbd.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import cl.tbd.proyectotbd.models.Emergency;

//import java.sql.Date;
import java.util.List;

@Repository
public class EmergencyRepositoryImp implements EmergencyRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public int countEmergencies() {
        int total = 0;
        try(Connection conn = sql2o.open()){
            total = conn.createQuery("SELECT COUNT(*) FROM emergency").executeScalar(Integer.class);
        }
        return total;
    }

    @Override
    public List<Emergency> getAllEmergencies() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM emergency")
                    .executeAndFetch(Emergency.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Emergency createEmergency(Emergency emergency) {
        try(Connection conn = sql2o.open()){
            int insertedId = (int) conn.createQuery("INSERT INTO emergency (name, description, start_date, end_date, id_institution) VALUES (:emergencyName, :description, :startDate, :endDate, :idInstitution)", true)
                    .addParameter("emergencyName", emergency.getName())
                    .addParameter("description", emergency.getDescription())
                    .addParameter("startDate", emergency.getStart_date())
                    .addParameter("endDate", emergency.getEnd_date())
                    .addParameter("idInstitution", emergency.getId_institution())
                    .executeUpdate().getKey();
            emergency.setId(insertedId);
            return emergency;        
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
}

