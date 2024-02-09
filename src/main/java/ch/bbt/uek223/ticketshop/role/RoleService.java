package ch.bbt.uek223.ticketshop.role;


import org.springframework.stereotype.Service;

@Service
public class RoleService {
    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }
}
