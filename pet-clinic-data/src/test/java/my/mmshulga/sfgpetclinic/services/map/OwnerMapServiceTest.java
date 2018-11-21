package my.mmshulga.sfgpetclinic.services.map;

import my.mmshulga.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    private OwnerMapService ownerMapService;
    private static final Long testId = 1L;
    private static final String testLastName = "Smith";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetMapService(), new PetTypeMapService());
        Owner testOwner = Owner.builder()
                .id(testId)
                .lastName(testLastName)
                .build();

        ownerMapService.save(testOwner);
    }

    @Test
    void saveWithExistingId() {
        Long id = 2L;
        Owner o1 = Owner.builder().id(id).build();
        Owner s1 = ownerMapService.save(o1);
        assertEquals(id, s1.getId());
    }

    @Test
    void saveNoId() {
        Owner o = Owner.builder().build();
        Owner so = ownerMapService.save(o);

        assertNotNull(so);
        assertNotNull(so.getId());
    }

    @Test
    void findByLastNameExisting() {
        Owner found = ownerMapService.findByLastName(testLastName);
        assertNotNull(found.getLastName());
        assertEquals(testId, found.getId());
        assertEquals(testLastName, found.getLastName());
    }

    @Test
    void findByLastNameNotExisting() {
        Owner found = ownerMapService.findByLastName("crap");
        assertNull(found);
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(testId);
        assertEquals(testId, owner.getId());
    }

    @Test
    void findAll() {
        Set<Owner> all = ownerMapService.findAll();
        assertEquals(1, all.size());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(testId));
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(testId);
        assertEquals(0, ownerMapService.findAll().size());
    }
}