package ua.berlinets.s28359.TPO_PRO.services;

import org.springframework.stereotype.Service;
import ua.berlinets.s28359.TPO_PRO.entities.Role;
import ua.berlinets.s28359.TPO_PRO.enums.RoleEnum;
import ua.berlinets.s28359.TPO_PRO.repositories.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByRoleName(RoleEnum role) {
        return roleRepository.findByRole(role);
    }
}
