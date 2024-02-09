package ch.bbt.uek223.ticketshop.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);
    Boolean existsByName(String name);
}