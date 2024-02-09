package ch.bbt.uek223.ticketshop.event;

import ch.bbt.uek223.ticketshop.event.dto.EventDto;
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
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(eventService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable int id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventDto eventDto) {
        return new ResponseEntity<>(eventService.create(eventDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateEvent(@Valid @RequestBody EventDto eventDto, @PathVariable int id) {
        return ResponseEntity.ok(eventService.update(eventDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable int id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
