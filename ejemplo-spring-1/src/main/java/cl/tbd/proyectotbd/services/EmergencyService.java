package cl.tbd.proyectotbd.services;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.tbd.proyectotbd.models.Emergency;
import cl.tbd.proyectotbd.repositories.EmergencyRepository;

import java.util.List;

@CrossOrigin
@RestController
public class EmergencyService {

    private final EmergencyRepository emergencyRepository;
    EmergencyService(EmergencyRepository emergencyRepository){
        this.emergencyRepository = emergencyRepository;
    }

    @GetMapping("/emergencies")
    public List<Emergency> getAllEmergencies() {
        return emergencyRepository.getAllEmergencies();
    }

    @GetMapping("/emergencies/count")
    public String countEmergencies(){
        int total = emergencyRepository.countEmergencies();
        return String.format("Hay %s emergencias!", total);
    }

    @PostMapping("/emergencies")
    @ResponseBody
    public Emergency createEmergency(@RequestBody Emergency emergency){
        Emergency result = emergencyRepository.createEmergency(emergency);
        return result;
    }
}