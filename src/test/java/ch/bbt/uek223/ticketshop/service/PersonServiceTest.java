package ch.bbt.uek223.ticketshop.service;


import ch.bbt.uek223.ticketshop.DataDTOUtil;
import ch.bbt.uek223.ticketshop.DataUtil;
import ch.bbt.uek223.ticketshop.role.*;
import ch.bbt.uek223.ticketshop.person.*;
import ch.bbt.uek223.ticketshop.person.dto.PersonRequestDto;
import ch.bbt.uek223.ticketshop.person.dto.PersonResponseDto;
import ch.bbt.uek223.ticketshop.security.AuthRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@Order(30)
@ExtendWith(SpringExtension.class)
public class PersonServiceTest {
    private static final AuthRequestDTO testAuthRequestDto = new AuthRequestDTO();
    private final ArgumentCaptor<Person> argumentCaptorPerson = ArgumentCaptor.forClass(Person.class);

    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleService roleService;

    @Spy
    private PersonMapper personMapper;

    @BeforeAll
    public static void setup() {
        testAuthRequestDto.setEmail("test@example.com");
        testAuthRequestDto.setPassword("VerySecurePassword");
    }

    @Test
    @Order(1)
    void checkFindById_whenValidId_thenPersonDTOIsReturned() {
        PersonResponseDto expectedDTO = DataDTOUtil.getTestPersonResponseDTO();
        Person expectedPerson = DataUtil.getTestPerson();

        Mockito.when(personRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(personRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(personRepository.findById(eq(1))).thenReturn(Optional.of(expectedPerson));

        PersonResponseDto actual = personService.findById(1);

        assertEquals(expectedDTO.getId(), actual.getId());
        assertEquals(expectedDTO.getEmail(), actual.getEmail());
        assertEquals(expectedDTO.getEventIds(), actual.getEventIds());
    }

    @Test
    @Order(1)
    void checkFindById_whenInvalidId_thenThrowEntityNotFound() {
        Mockito.when(personRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(personRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(personRepository.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> personService.findById(0));
    }

    @Test
    @Order(4)
    void checkCreate_whenValidPersonSignUpDTO_thenReturnPersonDTO() {
        Person expectedPerson = DataUtil.getTestPerson();
        PersonResponseDto expectedDTO = DataDTOUtil.getTestPersonResponseDTO();

        Mockito.when(roleService.findRoleByName(any())).thenReturn(new Role());

        Mockito.when(personRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(personRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));


        Mockito.when(personRepository.save(any(Person.class))).thenReturn(expectedPerson);
        Mockito.when(personRepository.findById(anyInt())).thenReturn(Optional.of(expectedPerson));


        PersonResponseDto actual = personService.create(testAuthRequestDto);

        assertEquals(expectedDTO.getId(), actual.getId());
        assertEquals(expectedDTO.getEmail(), actual.getEmail());
        assertEquals(expectedDTO.getEventIds(), actual.getEventIds());
    }

    @Test
    @Order(4)
    void checkCreate_whenEmailAlreadyExisting_thenThrowConstraintViolation() {
        Mockito.when(roleService.findRoleByName(any())).thenReturn(new Role());
        Mockito.when(personRepository.save(any(Person.class))).thenThrow(ConstraintViolationException.class);
        assertThrows(ConstraintViolationException.class, () -> personService.create(testAuthRequestDto));
    }

    @Test
    @Order(5)
    void checkUpdate_whenValidPersonDTO_thenChangedPersonDTOIsReturned() {
        String newEmail = "NewEmail@foo.bar";
        String newPassword = "NewPassword";

        Person unchangedPerson = DataUtil.getTestPerson();

        Person changedPerson = DataUtil.getTestPerson();
        changedPerson.setEmail(newEmail);
        changedPerson.setPassword(newPassword);

        PersonRequestDto expectedPersonRequestDTO = DataDTOUtil.getTestPersonRequestDTO();
        expectedPersonRequestDTO.setEmail(newEmail);

        Mockito.when(personRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(personRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(personRepository.findById(eq(1))).thenReturn(Optional.of(unchangedPerson));
        Mockito.when(personRepository.save(any(Person.class))).thenReturn(changedPerson);

        PersonResponseDto actualPersonResponseDTO = personService.update(expectedPersonRequestDTO, 1);

        Mockito.verify(personRepository).save(argumentCaptorPerson.capture());

        Person verify = argumentCaptorPerson.getValue();

        assertNotEquals("password1", verify.getPassword());
        assertEquals(expectedPersonRequestDTO.getEmail(), actualPersonResponseDTO.getEmail());
    }

    @Test
    @Order(5)
    void checkUpdate_whenInvalidPersonId_thenThrowEntityNotFound() {
        PersonRequestDto testPersonRequestDTO = DataDTOUtil.getTestPersonRequestDTO();

        Mockito.when(personRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(personRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(personRepository.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> personService.update(testPersonRequestDTO, 0));
    }

    @Test
    @Order(2)
    void checkFindByEmail_whenExistingEmail_thenPersonDTOIsReturned() {
        Person expectedPerson = DataUtil.getTestPerson();
        PersonResponseDto expectedPersonResponseDTO = DataDTOUtil.getTestPersonResponseDTO();

        Mockito.when(personRepository.findPersonByEmail(anyString())).thenReturn(expectedPerson);

        PersonResponseDto actualPersonResponseDTO = personService.findByEmail("person1@foo.bar");

        assertEquals(expectedPersonResponseDTO.getId(), actualPersonResponseDTO.getId());
        assertEquals(expectedPersonResponseDTO.getEmail(), actualPersonResponseDTO.getEmail());
    }

    @Test
    @Order(2)
    void checkFindByEmail_whenNotExistingEmail_thenEntityNotFound() {
        Mockito.when(personRepository.findPersonByEmail(anyString())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> personService.findByEmail("42"));
    }

    @Test
    @Order(3)
    void checkExistsByEmail_whenExistingEmail_thenIsTrue() {
        Mockito.when(personRepository.existsByEmail(anyString())).thenReturn(true);

        assertTrue(personService.existsByEmail("existing@email.test"));
    }

    @Test
    @Order(3)
    void checkExistsByEmail_whenNotExistingEmail_thenIsFalse() {
        Mockito.when(personRepository.existsByEmail(anyString())).thenReturn(false);

        assertFalse(personService.existsByEmail("not@existing.email"));
    }
}
