package cl.tbd.ejemplo1.repositories;

import java.util.List;

import cl.tbd.ejemplo1.models.Tarea_habilidad;

public interface Tarea_habilidadRepository {

    public List<Tarea_habilidad> getAllTarea_habilidades();
    public Tarea_habilidad createTarea_habilidad(Tarea_habilidad tarea_habilidad);
    
}
