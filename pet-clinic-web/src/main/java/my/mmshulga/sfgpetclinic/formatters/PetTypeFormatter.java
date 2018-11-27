package my.mmshulga.sfgpetclinic.formatters;

import my.mmshulga.sfgpetclinic.model.PetType;
import my.mmshulga.sfgpetclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Set;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    @Autowired
    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String s, Locale locale) throws ParseException {
        Set<PetType> petTypes = petTypeService.findAll();
        return petTypes.stream()
                .filter(pt -> pt.getName().equals(s))
                .findFirst()
//                .orElse(null);
                .orElseThrow(() -> new ParseException("error parsing PetType in " + s, 0));
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
