package cl.tbd.ejemplo1.repositories;

import java.util.List;

import cl.tbd.ejemplo1.models.Eme_habilidad;

public interface Eme_habilidadRepository {

    public List<Eme_habilidad> getAllEme_habilidades();
    public Eme_habilidad createEme_habilidad(Eme_habilidad eme_habilidad);
    
}
