package cl.tbd.ejemplo1.services;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.tbd.ejemplo1.models.Eme_habilidad;
import cl.tbd.ejemplo1.repositories.Eme_habilidadRepository;


@CrossOrigin
@RestController
public class Eme_habilidadService {

    private final Eme_habilidadRepository eme_habilidadRepository;

    Eme_habilidadService(Eme_habilidadRepository eme_habilidadRepository){
        this.eme_habilidadRepository = eme_habilidadRepository;
    }

    @GetMapping("/eme_habilidades")
    public List<Eme_habilidad> getAllEme_habilidades() {
        return eme_habilidadRepository.getAllEme_habilidades();
    }

    @PostMapping("/tarea_habilidades")
    @ResponseBody
    public Eme_habilidad createEme_habilidad(@RequestBody Eme_habilidad eme_habilidad){
        Eme_habilidad result = eme_habilidadRepository.createEme_habilidad(eme_habilidad);
        return result;
    } 


    
}
