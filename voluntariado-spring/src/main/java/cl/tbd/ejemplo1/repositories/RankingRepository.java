package cl.tbd.ejemplo1.repositories;

import java.util.List;

import cl.tbd.ejemplo1.models.Ranking;

import org.json.JSONArray;

public interface RankingRepository {
    public List<Ranking> getAllRankings();
    public Ranking createRanking(Ranking ranking);
    public JSONArray getRankByTask(long id_tarea, int quantity);
}
