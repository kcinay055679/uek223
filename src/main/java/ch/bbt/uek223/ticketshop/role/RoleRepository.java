package ch.bbt.uek223.ticketshop.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);
    Boolean existsByName(String name);

    List<Role> getRolesByemail(String s);
}