package ch.bbt.uek223.ticketshop.service;

import ch.bbt.uek223.ticketshop.DataUtil;
import ch.bbt.uek223.ticketshop.person.*;
import ch.bbt.uek223.ticketshop.role.Role;
import ch.bbt.uek223.ticketshop.role.RoleService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
public class PersonRoleServiceTest {
    private final ArgumentCaptor<Person> argumentCaptorPerson = ArgumentCaptor.forClass(Person.class);
    @Mock
    private PersonRepository personRepository;
    @Mock
    private RoleService roleService;
    @InjectMocks
    private PersonService personService;

    @Test
    public void checkRemoveRole_whenExistingRole_thenDoesNotThrow() {
        Person personWithRole = DataUtil.getTestPersonWithRole();
        Person expected = DataUtil.getTestPerson();
        Role role = DataUtil.getTestRole();

        Mockito.when(personRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(personRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(personRepository.findById(eq(1))).thenReturn(Optional.of(personWithRole));
        Mockito.when(personRepository.save(any())).thenReturn(expected);
        Mockito.when(roleService.findRoleByName(anyString())).thenReturn(role);

        personService.removeRole(1, "ADMIN");

        Mockito.verify(personRepository).save(argumentCaptorPerson.capture());
        Person verify = argumentCaptorPerson.getValue();

        Assertions.assertEquals(0, verify.getAssignedRoles().size(), "Sizes did not match");
    }
}
