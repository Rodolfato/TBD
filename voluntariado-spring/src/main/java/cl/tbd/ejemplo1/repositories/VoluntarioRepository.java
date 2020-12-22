package cl.tbd.ejemplo1.repositories;

import java.util.List;
import java.util.Map;

import cl.tbd.ejemplo1.models.Voluntario;

public interface VoluntarioRepository {
    public int countVoluntarios();
    public List<Voluntario> getAllVoluntarios();
    public Voluntario createVoluntario(Voluntario voluntario);
    public Voluntario updateVoluntario(Voluntario voluntario, long id);
    public List<Voluntario> deleteVoluntario(long id);
    public List<Map<String,Object>> getWithinRadius(long id, double radius);
    public List<Voluntario> getOnlyVoluntariosWithTasks();
}
