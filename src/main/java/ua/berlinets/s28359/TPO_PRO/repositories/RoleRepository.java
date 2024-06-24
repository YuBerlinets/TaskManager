package ua.berlinets.s28359.TPO_PRO.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.berlinets.s28359.TPO_PRO.entities.Role;
import ua.berlinets.s28359.TPO_PRO.enums.RoleEnum;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(RoleEnum role);

}
