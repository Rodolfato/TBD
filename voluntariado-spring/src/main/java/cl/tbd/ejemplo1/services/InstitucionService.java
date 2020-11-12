package cl.tbd.ejemplo1.services;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.tbd.ejemplo1.models.Institucion;
import cl.tbd.ejemplo1.repositories.InstitucionRepository;

@CrossOrigin
@RestController
public class InstitucionService {

    private final InstitucionRepository institucionRepository;

    InstitucionService(InstitucionRepository institucionRepository){
        this.institucionRepository = institucionRepository;
    }

    @GetMapping("/instituciones")
    public List<Institucion> getAllInstituciones() {
        return institucionRepository.getAllInstituciones();
    }

    @PostMapping("/instituciones")
    @ResponseBody
    public Institucion createInstitucion(@RequestBody Institucion institucion){
        Institucion result = institucionRepository.createInstitucion(institucion);
        return result;
    }
    
}
