package my.mmshulga.sfgpetclinic.controller;

import my.mmshulga.sfgpetclinic.model.Pet;
import my.mmshulga.sfgpetclinic.model.Visit;
import my.mmshulga.sfgpetclinic.services.PetService;
import my.mmshulga.sfgpetclinic.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class VisitController {
    private final VisitService visitService;
    private final PetService petService;

    @Autowired
    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadEmptyVisit(@PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);
        model.addAttribute("pet", pet);
        Visit visit = Visit.builder().pet(pet).build();
        pet.getVisits().add(visit);
        return visit;
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String createForm(@PathVariable Long petId) {
        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String doCreate(@Valid Visit visit, BindingResult bindingResult, @PathVariable Long ownerId) {
        if (bindingResult.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        }

        visitService.save(visit);
        return "redirect:/owners/" + ownerId;
    }
}
