package my.mmshulga.sfgpetclinic.controller;

import my.mmshulga.sfgpetclinic.model.Owner;
import my.mmshulga.sfgpetclinic.model.Pet;
import my.mmshulga.sfgpetclinic.model.PetType;
import my.mmshulga.sfgpetclinic.model.Visit;
import my.mmshulga.sfgpetclinic.services.PetService;
import my.mmshulga.sfgpetclinic.services.VisitService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    private VisitService visitService;

    @Mock
    private PetService petService;

    @InjectMocks
    private VisitController visitController;

    private Pet exampleCat;
    private PetType catPetType;
    private Owner exampleOwner;
    private Visit visit;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        exampleOwner = Owner.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .city("Moscow")
                .telephone("Phone")
                .pets(new HashSet<>())
                .build();

        catPetType = PetType.builder().id(1L).name("Cat").build();

        exampleCat = Pet.builder()
                .id(1L)
                .name("Some pet")
                .birthDate(LocalDate.now())
                .petType(catPetType)
                .owner(exampleOwner)
                .visits(new HashSet<>())
                .build();

        exampleOwner.getPets().add(exampleCat);

        visit = Visit.builder()
                .pet(exampleCat)
                .date(LocalDate.now())
                .description("Some description")
                .build();

        exampleCat.getVisits().add(visit);

        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void testCreateForm() throws Exception{
        when(petService.findById(anyLong())).thenReturn(exampleCat);

        mockMvc.perform(get("/owners/1/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void testDoCreate() throws Exception {
        when(visitService.save(any(Visit.class))).thenReturn(visit);
        when(petService.findById(anyLong())).thenReturn(exampleCat);

        mockMvc.perform(post("/owners/1/pets/1/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }
}