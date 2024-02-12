package ch.bbt.uek223.ticketshop.security;

import ch.bbt.uek223.ticketshop.person.Person;
import ch.bbt.uek223.ticketshop.person.PersonRepository;
import ch.bbt.uek223.ticketshop.role.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<SimpleGrantedAuthority> list = optionalPerson.getAssignedRoles()
                .stream().map(Role::getName).map(SimpleGrantedAuthority::new).toList();

        return new User(optionalPerson.getEmail(), optionalPerson.getPassword(), list);
    }
}
