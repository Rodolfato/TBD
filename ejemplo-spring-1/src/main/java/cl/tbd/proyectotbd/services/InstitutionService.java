package cl.tbd.proyectotbd.services;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.tbd.proyectotbd.models.Institution;
import cl.tbd.proyectotbd.repositories.InstitutionRepository;

import java.util.List;

@CrossOrigin
@RestController
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    InstitutionService(InstitutionRepository institutionRepository){
        this.institutionRepository = institutionRepository;
    }

    @GetMapping("/institutions")
    public List<Institution> getAllInstitutions() {
        return institutionRepository.getAllInstitutions();
    }

    @GetMapping("/institutions/count")
    public String countInstitutions(){
        int total = institutionRepository.countInstitutions();
        return String.format("Hay %s instituciones!", total);
    }

    @PostMapping("/institutions")
    @ResponseBody
    public Institution createInstitution(@RequestBody Institution institution){
        Institution result = institutionRepository.createInstitution(institution);
        return result;
    }
}
