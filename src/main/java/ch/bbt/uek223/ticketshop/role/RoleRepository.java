package ch.bbt.uek223.ticketshop.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);

    Boolean existsByName(String name);

    @Query("SELECT r FROM Role r JOIN r.persons p WHERE p.email = :email")
    List<Role> getRolesByemail(String email);
}