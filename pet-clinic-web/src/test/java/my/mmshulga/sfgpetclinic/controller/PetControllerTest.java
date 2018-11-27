package my.mmshulga.sfgpetclinic.controller;

import my.mmshulga.sfgpetclinic.model.Owner;
import my.mmshulga.sfgpetclinic.model.Pet;
import my.mmshulga.sfgpetclinic.model.PetType;
import my.mmshulga.sfgpetclinic.services.OwnerService;
import my.mmshulga.sfgpetclinic.services.PetService;
import my.mmshulga.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {
    @Mock private PetService petService;
    @Mock private PetTypeService petTypeService;
    @Mock private OwnerService ownerService;
    @InjectMocks private PetController petController;

    private Owner exampleOwner;
    private Pet examplePet;
    private Set<PetType> petTypes;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        exampleOwner = Owner.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .city("Moscow")
                .telephone("12-34-56")
                .address("123, Serious str.")
                .pets(new HashSet<>())
                .build();


        PetType dog = PetType.builder().id(1L).name("Dog").build();
        PetType cat = PetType.builder().id(1L).name("Cat").build();

        petTypes = new HashSet<>();
        petTypes.add(dog);
        petTypes.add(cat);

        examplePet = Pet.builder()
                .id(1L)
                .name("Test")
                .petType(dog)
                .birthDate(LocalDate.now())
//                .owner(exampleOwner)
                .build();

//        exampleOwner.getPets().add(examplePet);
    }

    @Test
    void initCreationForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(exampleOwner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));

        verify(ownerService, times(1)).findById(anyLong());
    }

    @Test
    void doCreate() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(exampleOwner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService, times(1)).save(any(Pet.class));
    }

    @Test
    void initUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(exampleOwner);
        when(petService.findById(anyLong())).thenReturn(examplePet);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get("/owners/1/pets/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));

        verify(ownerService, times(1)).findById(anyLong());
    }

    @Test
    void doUpdate() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(exampleOwner);
//        when(petService.findById(anyLong())).thenReturn(examplePet);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService, times(1)).save(any(Pet.class));
    }
}