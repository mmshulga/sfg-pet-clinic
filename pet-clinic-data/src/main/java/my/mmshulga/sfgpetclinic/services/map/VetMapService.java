package my.mmshulga.sfgpetclinic.services.map;

import my.mmshulga.sfgpetclinic.model.Vet;
import my.mmshulga.sfgpetclinic.services.VetService;

public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {
    @Override
    public Vet save(Vet object) {
        return save(object.getId(), object);
    }
}
