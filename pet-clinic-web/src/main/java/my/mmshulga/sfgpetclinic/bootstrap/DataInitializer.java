package my.mmshulga.sfgpetclinic.bootstrap;

import my.mmshulga.sfgpetclinic.model.Owner;
import my.mmshulga.sfgpetclinic.model.Vet;
import my.mmshulga.sfgpetclinic.services.OwnerService;
import my.mmshulga.sfgpetclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    @Autowired
    public DataInitializer(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {
        int numCreated = 10;
        for (long i = 0; i < numCreated; i++) {
            Owner owner = new Owner();
            owner.setFirstName("OwnerFirstName#"+i);
            owner.setLastName("OwnerLastName#"+i);
            owner.setId(i);
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
