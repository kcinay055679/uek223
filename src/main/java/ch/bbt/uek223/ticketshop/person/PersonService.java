package ch.bbt.uek223.ticketshop.person;

import ch.bbt.uek223.ticketshop.event.Event;
import ch.bbt.uek223.ticketshop.person.dto.PersonRequestDto;
import ch.bbt.uek223.ticketshop.person.dto.PersonResponseDto;
import ch.bbt.uek223.ticketshop.role.Role;
import ch.bbt.uek223.ticketshop.role.RoleService;
import ch.bbt.uek223.ticketshop.security.AuthRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByEmail(String mail) {
        return personRepository.existsByEmail(mail);
    }

    public List<GrantedAuthority> getRolesByUserEmail(String email) {
        return roleService.getRolesByUserEmail(email).stream().map(role -> (GrantedAuthority) role::getName).toList();
    }

    public PersonResponseDto findByEmail(String number) {
        return personMapper.toDto(personRepository.findPersonByEmail(number));
    }

    public PersonResponseDto update(PersonRequestDto testPersonRequestDTO, int i) {
        Person person = personRepository.findById(i).orElseThrow(EntityNotFoundException::new);
        person.setEmail(testPersonRequestDTO.getEmail());
        person.setPassword(passwordEncoder.encode(testPersonRequestDTO.getPassword()));
        person.setEvents(testPersonRequestDTO.getEventIds().stream().map(id -> new Event().setId(id)).collect(Collectors.toSet()));
        return personMapper.toDto(personRepository.save(person));
    }

    public PersonResponseDto findById(int i) {
        return personMapper.toDto(personRepository.findById(i).orElseThrow(EntityNotFoundException::new));
    }

    public PersonResponseDto create(@Valid AuthRequestDTO testAuthRequestDto) {
        Person person = new Person();
        person.setEmail(testAuthRequestDto.getEmail());
        person.setPassword(passwordEncoder.encode(testAuthRequestDto.getPassword()));
        person.setEvents(new HashSet<>());
        return personMapper.toDto(personRepository.save(person));
    }

    public void removeRole(int eq, String s) {
        Person person = personRepository.findById(eq).orElseThrow(EntityNotFoundException::new);
        person.setAssignedRoles(person.getAssignedRoles().stream().filter(role -> !role.getName().equals(s)).collect(Collectors.toSet()));
        personRepository.save(person);
    }

    public List<String> assignRole(int eq, String s)
    {
        Person person = personRepository.findById(eq).orElseThrow(EntityNotFoundException::new);
        Role role = roleService.findRoleByName(s);
        person.getAssignedRoles().add(role);
        return personRepository.save(person).getAssignedRoles().stream().map(Role::getName).toList();
    }

    public void delete(int id) {
        Person person = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        personRepository.delete(person);
    }
}
