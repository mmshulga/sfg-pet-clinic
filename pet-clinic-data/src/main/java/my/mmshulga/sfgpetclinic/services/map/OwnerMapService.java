package my.mmshulga.sfgpetclinic.services.map;

import my.mmshulga.sfgpetclinic.model.Owner;
import my.mmshulga.sfgpetclinic.model.Pet;
import my.mmshulga.sfgpetclinic.model.PetType;
import my.mmshulga.sfgpetclinic.services.OwnerService;
import my.mmshulga.sfgpetclinic.services.PetService;
import my.mmshulga.sfgpetclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("map")
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetService petService;
    private final PetTypeService petTypeService;

    @Autowired
    public OwnerMapService(PetService petService, PetTypeService petTypeService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @Override
    public Owner save(Owner object) {
        if (object.getPets() != null) {
            object.getPets().forEach(pet -> {
                PetType petType = pet.getPetType();
                if (petType != null) {
                    if (petType.getId() == null) {
                        PetType savedPetType = petTypeService.save(petType);
                        pet.setPetType(savedPetType);
                    }
                }
                else throw new RuntimeException("Pet type required!");

                if (pet.getId() == null) {
                    Pet savedPet = petService.save(pet);
                    pet.setId(savedPet.getId());
                }
            });
        }

        return save(counter.getAndIncrement(), object);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}
