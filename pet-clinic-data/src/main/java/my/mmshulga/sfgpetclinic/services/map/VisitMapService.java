package my.mmshulga.sfgpetclinic.services.map;

import my.mmshulga.sfgpetclinic.model.Visit;
import my.mmshulga.sfgpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("map")
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {
    @Override
    public Visit save(Visit object) {
        if (object.getId() != null) {
            return save(object.getId(), object);
        }
        else {
            return save(counter.getAndIncrement(), object);
        }
    }
}
