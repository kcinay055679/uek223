package ch.bbt.uek223.ticketshop.ticket.dto;

import ch.bbt.uek223.ticketshop.ticket.Ticket;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Ticket}
 */
public record TicketDto(Integer id, Integer amount, @Size(max = 255) String description, @Size(max = 255) String name,
                        Integer eventId) implements Serializable {
}