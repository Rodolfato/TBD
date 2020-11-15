package cl.tbd.ejemplo1.services;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.tbd.ejemplo1.models.Tarea_habilidad;
import cl.tbd.ejemplo1.repositories.Tarea_habilidadRepository;

@CrossOrigin
@RestController
public class Tarea_habilidadService {

    private final Tarea_habilidadRepository tarea_habilidadRepository;

    Tarea_habilidadService(Tarea_habilidadRepository tarea_habilidadRepository){
        this.tarea_habilidadRepository = tarea_habilidadRepository;
    }

    @GetMapping("/tarea_habilidades")
    public List<Tarea_habilidad> getAllTarea_habilidades() {
        return tarea_habilidadRepository.getAllTarea_habilidades();
    }

    @PostMapping("/tarea_habilidades")
    @ResponseBody
    public Tarea_habilidad createTarea_habilidad(@RequestBody Tarea_habilidad tarea_habilidad){
        Tarea_habilidad result = tarea_habilidadRepository.createTarea_habilidad(tarea_habilidad);
        return result;
    }    
}
