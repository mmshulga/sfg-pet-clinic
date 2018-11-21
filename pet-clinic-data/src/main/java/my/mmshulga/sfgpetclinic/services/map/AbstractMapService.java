package my.mmshulga.sfgpetclinic.services.map;

import my.mmshulga.sfgpetclinic.model.BaseEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {
    protected Map<ID, T> map = new HashMap<>();
    protected final AtomicLong counter = new AtomicLong(0);

    public T findById(ID id) {
        return map.get(id);
    }

    public T save(ID id, T object) {
        map.put(id, object);
        object.setId(id);
        return object;
    }

    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    public void delete(T object) {
        map.entrySet().removeIf((e -> e.getValue().equals(object)));
    }

    public void deleteById(ID id) {
        map.remove(id);
    }
}
