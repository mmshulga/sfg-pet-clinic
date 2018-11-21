package my.mmshulga.sfgpetclinic.services.springdatajpa;

import my.mmshulga.sfgpetclinic.model.Owner;
import my.mmshulga.sfgpetclinic.repositories.OwnerRepository;
import my.mmshulga.sfgpetclinic.repositories.PetRepository;
import my.mmshulga.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private static final String SMITH_LAST_NAME = "Smith";
    private static final Long SMITH_ID = 1L;

    private Owner someOwner;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetTypeRepository petTypeRepository;

    @InjectMocks
    private OwnerSDJpaService ownerService;

    @BeforeEach
    void setUp() {
         someOwner = Owner.builder()
                 .id(SMITH_ID)
                 .lastName(SMITH_LAST_NAME)
                 .build();
    }

    @Test
    void findByLastName() {
        when(ownerService.findByLastName(SMITH_LAST_NAME)).thenReturn(someOwner);
        Owner smith = ownerService.findByLastName(SMITH_LAST_NAME);

        assertEquals(SMITH_LAST_NAME, smith.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findById() {
        // when
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(someOwner));

        // then
        Owner owner = ownerService.findById(SMITH_ID);
        assertNotNull(owner);
        verify(ownerRepository, times(1)).findById(SMITH_ID);
    }

    @Test
    void save() {
        // given
        Owner ownerToSave = Owner.builder().id(1L).build();

        // when
        when(ownerRepository.save(any())).thenReturn(someOwner);

        // then
        Owner savedOwner = ownerService.save(ownerToSave);
        assertNotNull(savedOwner);
        verify(ownerRepository, times(1)).save(any());
    }

    @Test
    void findAll() {
        // given
        Set<Owner> returnedSet = new HashSet<>();
        returnedSet.add(Owner.builder().id(1L).build());
        returnedSet.add(Owner.builder().id(2L).build());

        // when
        when(ownerService.findAll()).thenReturn(returnedSet);

        // then
        Set<Owner> owners = ownerService.findAll();
        assertNotNull(owners);
        assertEquals(2, owners.size());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void delete() {
        ownerService.delete(someOwner);
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerService.deleteById(1L);
        verify(ownerRepository, times(1)).deleteById(any());
    }
}