package cl.tbd.proyectotbd.repositories;
import java.util.List;

import cl.tbd.proyectotbd.models.Institution;

public interface InstitutionRepository {
    public int countInstitutions();
    public List<Institution> getAllInstitutions();
    public Institution createInstitution(Institution institution);
}
