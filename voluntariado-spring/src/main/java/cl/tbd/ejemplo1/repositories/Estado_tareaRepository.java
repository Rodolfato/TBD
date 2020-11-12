package cl.tbd.ejemplo1.repositories;

import java.util.List;

import cl.tbd.ejemplo1.models.Estado_tarea;

public interface Estado_tareaRepository {
    public List<Estado_tarea> getAllEstado_tareas();
    public Estado_tarea createEstado_tarea(Estado_tarea estado_tarea);
}
