package ch.bbt.uek223.ticketshop.service;

import ch.bbt.uek223.ticketshop.DataUtil;
import ch.bbt.uek223.ticketshop.person.Person;
import ch.bbt.uek223.ticketshop.person.PersonRepository;
import ch.bbt.uek223.ticketshop.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private PersonRepository personRepository;

    @Test
    void checkLoadByUsername_whenValidEmail_thenReturnUser() {
        String email = "test@test.ch";
        Person person = DataUtil.getTestPerson().setEmail(email);
        Mockito.when(personRepository.findPersonByEmail(email)).thenReturn(person);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
    }
//    Fast, Isolated, Repeatable, Self-Validating, Timely
    @Test
    void checkLoadByUsername_whenInvalidEmail_thenThrowError() {
        String email = "test@test.ch";
        Mockito.when(personRepository.findPersonByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(email));
    }
}
