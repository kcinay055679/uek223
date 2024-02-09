package ch.bbt.uek223.ticketshop.event;

import ch.bbt.uek223.ticketshop.event.dto.EventDto;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(EventController.PATH)
public class EventController {
    public static final String PATH = "/events";
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(eventService.findAll());
    }
    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody EventDto eventDto) {
        return new ResponseEntity<>(eventService.create(eventDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody EventDto eventDto, @PathVariable int id) {
        return ResponseEntity.ok(eventService.update(eventDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
