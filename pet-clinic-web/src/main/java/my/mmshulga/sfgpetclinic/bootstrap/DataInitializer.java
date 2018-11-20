package my.mmshulga.sfgpetclinic.bootstrap;

import my.mmshulga.sfgpetclinic.model.*;
import my.mmshulga.sfgpetclinic.services.OwnerService;
import my.mmshulga.sfgpetclinic.services.PetTypeService;
import my.mmshulga.sfgpetclinic.services.SpecialtyService;
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
    private final SpecialtyService specialtyService;

    @Autowired
    public DataInitializer(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService) {
        this.ownerService       = ownerService;
        this.vetService         = vetService;
        this.petTypeService     = petTypeService;
        this.specialtyService   = specialtyService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if (count == 0L) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("dog");
        PetType savedDog = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("cat");
        PetType savedCat = petTypeService.save(cat);

        long numCreated = 10;
        for (long i = 0; i < numCreated; i++) {
            Owner owner = new Owner();
            owner.setFirstName("OwnerFirstName#"+i);
            owner.setLastName("OwnerLastName#"+i);
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
            pet.setBirthDate(LocalDate.now());
            pet.setName("Pet #"+i);

            owner.getPets().add(pet);

            ownerService.save(owner);
        }
        System.out.println("Loaded owners ...");

        Specialty radiology = new Specialty();
        radiology.setDescription("radiology");
        Specialty radiologySaved = specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("surgery");
        Specialty surgerySaved = specialtyService.save(surgery);

        for (long i = 0; i < numCreated; i++) {
            Vet vet = new Vet();
            vet.setFirstName("VetFirstName#"+i);
            vet.setLastName("VetLastName#"+i);

            if (i < numCreated / 2) {
                vet.getSpecialties().add(radiologySaved);
            }
            else {
                vet.getSpecialties().add(surgerySaved);
            }

            vetService.save(vet);
        }
        System.out.println("Loaded pets ...");
    }
}
