package my.mmshulga.sfgpetclinic.services.map;

import my.mmshulga.sfgpetclinic.model.PetType;
import my.mmshulga.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

@Service
public class PetTypeMapService extends AbstractMapService<PetType, Long> implements PetTypeService {
    @Override
    public PetType save(PetType object) {
        return save(object.getId(), object);
    }
}
