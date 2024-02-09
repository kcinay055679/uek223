package ch.bbt.uek223.ticketshop.person;

import ch.bbt.uek223.ticketshop.event.Event;
import ch.bbt.uek223.ticketshop.person.dto.PersonRequestDto;
import ch.bbt.uek223.ticketshop.person.dto.PersonResponseDto;
import ch.bbt.uek223.ticketshop.role.RoleService;
import ch.bbt.uek223.ticketshop.security.AuthRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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
}
