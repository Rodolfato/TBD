package cl.tbd.proyectotbd.repositories;
import java.util.List;
import cl.tbd.proyectotbd.models.Emergency;

public interface EmergencyRepository {
    public int countEmergencies();
    public List<Emergency> getAllEmergencies();
    public Emergency createEmergency(Emergency emergency);    
}
