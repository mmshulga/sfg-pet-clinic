package my.mmshulga.sfgpetclinic.services.map;

import my.mmshulga.sfgpetclinic.model.Pet;
import my.mmshulga.sfgpetclinic.services.PetService;

public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {
    @Override
    public Pet save(Pet object) {
        return save(object.getId(), object);
    }
}
