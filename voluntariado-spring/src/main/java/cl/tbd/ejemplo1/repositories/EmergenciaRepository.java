package cl.tbd.ejemplo1.repositories;

import java.util.List;

import cl.tbd.ejemplo1.models.Emergencia;

public interface EmergenciaRepository {
    public List<Emergencia> getAllEmergencias();
    public Emergencia createEmergencia(Emergencia emergencia);
}
