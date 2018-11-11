package my.mmshulga.sfgpetclinic.services;

import my.mmshulga.sfgpetclinic.model.Owner;

import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
