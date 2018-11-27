package my.mmshulga.sfgpetclinic.controller;

import my.mmshulga.sfgpetclinic.model.Owner;
import my.mmshulga.sfgpetclinic.model.Pet;
import my.mmshulga.sfgpetclinic.model.PetType;
import my.mmshulga.sfgpetclinic.services.OwnerService;
import my.mmshulga.sfgpetclinic.services.PetService;
import my.mmshulga.sfgpetclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {
    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    @Autowired
    public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @ModelAttribute("types")
    public Collection<String> populatePetTypes() {
        return petTypeService.findAll().stream().map(PetType::getName).collect(Collectors.toSet());
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String creationForm(Owner owner, Model model) {
        Pet pet = Pet.builder().owner(owner).build();
        owner.getPets().add(pet);
        model.addAttribute("pet", pet);
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/new")
    public String doCreate(Owner owner,
                           @Valid Pet pet,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        }

        pet.setOwner(owner);
        Pet savedPet = petService.save(pet);
        owner.getPets().add(savedPet);
        ownerService.save(owner);
        return "redirect:/owners/" + owner.getId();
    }

    @GetMapping("/pets/{petId}/edit")
    public String updateForm(@PathVariable Long petId, Model model) {
        model.addAttribute("pet", petService.findById(petId));
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/{petId}/edit")
    public String doUpdate(Owner owner,
                           @Valid Pet pet,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        }

        pet.setOwner(owner);
        petService.save(pet);
        return "redirect:/owners/" + owner.getId();
    }
}
