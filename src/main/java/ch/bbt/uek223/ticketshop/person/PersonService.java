package ch.bbt.uek223.ticketshop.person;

import ch.bbt.uek223.ticketshop.person.dto.PersonRequestDto;
import ch.bbt.uek223.ticketshop.person.dto.PersonResponseDto;
import ch.bbt.uek223.ticketshop.role.RoleService;
import ch.bbt.uek223.ticketshop.security.AuthRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public PersonService(PersonRepository personRepository, PersonMapper personMapper, RoleService roleService) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.roleService = roleService;
    }

    public boolean existsByEmail(String mail) {
        return personRepository.existsByEmail(mail);
    }

    public PersonResponseDto findByEmail(String number) {
        return personMapper.toDto(personRepository.findPersonByEmail(number));
    }

    public PersonResponseDto update(PersonRequestDto testPersonRequestDTO, int i) {

        return null;
    }

    public PersonResponseDto findById(int i) {
        return personMapper.toDto(personRepository.findById(i).orElseThrow(EntityNotFoundException::new));
    }

    public PersonResponseDto create(AuthRequestDTO testAuthRequestDto) {
        return null;
    }
}
