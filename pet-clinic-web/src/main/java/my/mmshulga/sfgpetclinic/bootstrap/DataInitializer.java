package my.mmshulga.sfgpetclinic.bootstrap;

import my.mmshulga.sfgpetclinic.model.Owner;
import my.mmshulga.sfgpetclinic.model.Vet;
import my.mmshulga.sfgpetclinic.services.OwnerService;
import my.mmshulga.sfgpetclinic.services.VetService;
import my.mmshulga.sfgpetclinic.services.map.OwnerMapService;
import my.mmshulga.sfgpetclinic.services.map.VetMapService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataInitializer() {
        ownerService = new OwnerMapService();
        vetService = new VetMapService();
    }

    @Override
    public void run(String... args) throws Exception {
        int numCreated = 10;
        for (long i = 0; i < numCreated; i++) {
            Owner owner = new Owner();
            owner.setFirstName("FirstName#"+i);
            owner.setLastName("LastName#"+i);
            owner.setId(i);
            ownerService.save(owner);
        }
        System.out.println("Loaded owners ...");

        for (long i = 0; i < numCreated; i++) {
            Vet vet = new Vet();
            vet.setFirstName("FirstName#"+i);
            vet.setLastName("LastName#"+i);
            vet.setId(i);
            vetService.save(vet);
        }
        System.out.println("Loaded pets ...");
    }
}
