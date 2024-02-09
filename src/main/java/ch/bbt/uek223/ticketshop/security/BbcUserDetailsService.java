package ch.bbt.uek223.ticketshop.security;

import ch.bbt.uek223.ticketshop.person.Person;
import ch.bbt.uek223.ticketshop.person.PersonRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BbcUserDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    public BbcUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person optionalPerson = personRepository.findPersonByEmail(email);

        if (optionalPerson == null) {
            throw new UsernameNotFoundException("No user found");
        }

        return new User(optionalPerson.getEmail(), optionalPerson.getPassword(), new ArrayList<>());
    }
}
