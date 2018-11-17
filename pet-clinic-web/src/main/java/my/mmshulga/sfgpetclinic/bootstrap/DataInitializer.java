package my.mmshulga.sfgpetclinic.bootstrap;

import my.mmshulga.sfgpetclinic.model.Owner;
import my.mmshulga.sfgpetclinic.model.Pet;
import my.mmshulga.sfgpetclinic.model.PetType;
import my.mmshulga.sfgpetclinic.model.Vet;
import my.mmshulga.sfgpetclinic.services.OwnerService;
import my.mmshulga.sfgpetclinic.services.PetTypeService;
import my.mmshulga.sfgpetclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    @Autowired
    public DataInitializer(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService   = ownerService;
        this.vetService     = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("dog");
        dog.setId(1L);
        PetType savedDog = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("cat");
        cat.setId(2L);
        PetType savedCat = petTypeService.save(cat);

        long numCreated = 10;
        for (long i = 0; i < numCreated; i++) {
            Owner owner = new Owner();
            owner.setFirstName("OwnerFirstName#"+i);
            owner.setLastName("OwnerLastName#"+i);
            owner.setId(i);
            owner.setAddress("Address for #"+i);
            owner.setCity("City#"+i);
            owner.setTelephone("telephone#"+i);

            Pet pet = new Pet();
            if (i < numCreated / 2) {
                pet.setPetType(savedDog);
            }
            else {
                pet.setPetType(savedCat);
            }
            pet.setOwner(owner);
            pet.setId(i);
            pet.setBirthDate(LocalDate.now());
            pet.setName("Pet #"+i);

            owner.getPets().add(pet);

            ownerService.save(owner);
        }
        System.out.println("Loaded owners ...");

        for (long i = 0; i < numCreated; i++) {
            Vet vet = new Vet();
            vet.setFirstName("VetFirstName#"+i);
            vet.setLastName("VetLastName#"+i);
            vet.setId(i);
            vetService.save(vet);
        }
        System.out.println("Loaded pets ...");
    }
}
