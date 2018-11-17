package my.mmshulga.sfgpetclinic.services.map;

import my.mmshulga.sfgpetclinic.model.Specialty;
import my.mmshulga.sfgpetclinic.services.SpecialtyService;
import org.springframework.stereotype.Service;

@Service
public class SpecialityMapService extends AbstractMapService<Specialty, Long> implements SpecialtyService {
    @Override
    public Specialty save(Specialty object) {
        return save(object.getId(), object);
    }
}
