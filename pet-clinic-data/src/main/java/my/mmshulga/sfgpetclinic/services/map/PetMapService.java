package my.mmshulga.sfgpetclinic.services.map;

import my.mmshulga.sfgpetclinic.model.Pet;
import my.mmshulga.sfgpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("map")
public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {
    @Override
    public Pet save(Pet object) {
        if (object.getId() != null) {
            return save(object.getId(), object);
        }
        else {
            return save(counter.getAndIncrement(), object);
        }
    }
}
