package ch.bbt.uek223.ticketshop.service;

import ch.bbt.uek223.ticketshop.DataUtil;
import ch.bbt.uek223.ticketshop.role.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
public class RoleServiceTest {
    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void checkFindRoleByUserEmail_whenValidEmailAndExistingRole_thenRoleListIsReturned() {
        List<Role> expectedRoleList = new ArrayList<>();
        Role expectedRole = new Role();
        expectedRole.setId(1);
        expectedRole.setName("ADMIN");
        expectedRoleList.add(expectedRole);

        Mockito.when(roleRepository.getRolesByemail(anyString())).thenReturn(expectedRoleList);

        assertSame(expectedRoleList, roleService.getRolesByUserEmail("person1@foo.bar"));
    }

    @Test
    public void checkFindRoleByName_whenValidName_thenRoleIsReturned() {
        Role expectedRole = DataUtil.getTestRole();

        Mockito.when(roleRepository.findRoleByName(anyString())).thenReturn(expectedRole);

        Role actualRole = roleService.findRoleByName("ADMIN");

        assertEquals(expectedRole.getId(), actualRole.getId());
        assertEquals(expectedRole.getName(), actualRole.getName());
    }
}
