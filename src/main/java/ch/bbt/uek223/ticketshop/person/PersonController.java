package ch.bbt.uek223.ticketshop.person;

import ch.bbt.uek223.ticketshop.person.dto.PersonRequestDto;
import ch.bbt.uek223.ticketshop.security.SecurityConstants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(PersonController.PATH)
public class PersonController {
    public static final String PATH = "/person";

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PatchMapping("/{id}")
    @RolesAllowed({SecurityConstants.ROLE_USER})
    public ResponseEntity<?> update(@Valid @RequestBody PersonRequestDto dto, @PathVariable int id) {
        return ResponseEntity.ok(personService.update(dto, id));
    }
}
