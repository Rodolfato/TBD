package cl.tbd.ejemplo1.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import cl.tbd.ejemplo1.models.Ranking;

@Repository
public class RankingRepositoryImp implements RankingRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Ranking> getAllRankings() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM ranking")
                    .executeAndFetch(Ranking.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Ranking createRanking(Ranking ranking) {
        try(Connection conn = sql2o.open()){
            long insertedId = (long) conn.createQuery("INSERT INTO ranking (id_voluntario, id_tarea, puntaje, flg_invitado, flg_participa) values (:idVol, :idTar, :rPuntaje, :rInv, :rPart)", true)
                    .addParameter("idVol", ranking.getId_voluntario())
                    .addParameter("idTar", ranking.getId_tarea())
                    .addParameter("rPuntaje", ranking.getPuntaje())
                    .addParameter("rInv", ranking.getFlg_invitado())
                    .addParameter("rPart", ranking.getFlg_participa())
                    .executeUpdate().getKey();
                    ranking.setId(insertedId);
            return ranking;        
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }        
    }
    
}
