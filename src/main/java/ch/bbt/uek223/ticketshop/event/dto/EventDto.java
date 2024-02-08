package ch.bbt.uek223.ticketshop.event.dto;

import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

/**
 * DTO for {@link ch.bbt.uek223.ticketshop.event.Event}
 */
public record EventDto(Integer id, Date date, @Size(max = 255) String description, @Size(max = 255) String name,
                       Integer ownerId, Set<Integer> ticketIds) implements Serializable {
}