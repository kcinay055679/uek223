package ch.bbt.uek223.ticketshop.ticket;

import ch.bbt.uek223.ticketshop.event.EventController;
import ch.bbt.uek223.ticketshop.event.dto.EventDto;
import ch.bbt.uek223.ticketshop.ticket.dto.TicketDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(TicketController.PATH)

public class TicketController {
    public static final String PATH = "/tickets";
private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(ticketService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.ok(ticketService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TicketDto dto) {
        return new ResponseEntity<>(ticketService.create(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody TicketDto dto, @PathVariable int id) {
        return ResponseEntity.ok(ticketService.update(dto,id));
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buy(@Valid @RequestBody List<TicketDto> dtos) {
        return ResponseEntity.ok(ticketService.buy(dtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
