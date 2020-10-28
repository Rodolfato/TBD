package cl.tbd.proyectotbd.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import cl.tbd.proyectotbd.models.Institution;

import java.util.List;

@Repository
public class InstitutionRepositoryImp implements InstitutionRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public int countInstitutions() {
        int total = 0;
        try(Connection conn = sql2o.open()){
            total = conn.createQuery("SELECT COUNT(*) FROM institution").executeScalar(Integer.class);
        }
        return total;
    }

    @Override
    public List<Institution> getAllInstitutions() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM institution")
                    .executeAndFetch(Institution.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Institution createInstitution(Institution institution) {
        try(Connection conn = sql2o.open()){
            int insertedId = (int) conn.createQuery("INSERT INTO institution (name, description) VALUES (:institutionName, :description)", true)
                    .addParameter("institutionName", institution.getName())
                    .addParameter("description", institution.getDescription())
                    .executeUpdate().getKey();
            institution.setId(insertedId);
            return institution;        
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
}
