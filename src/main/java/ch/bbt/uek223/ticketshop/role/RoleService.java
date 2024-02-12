package ch.bbt.uek223.ticketshop.role;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }

    public List<Role> getRolesByUserEmail(String s) {
        return roleRepository.getRolesByemail(s);
    }
}
