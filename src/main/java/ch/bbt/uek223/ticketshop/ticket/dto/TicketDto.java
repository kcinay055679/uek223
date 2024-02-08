package ch.bbt.uek223.ticketshop.ticket.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link ch.bbt.uek223.ticketshop.ticket.Ticket}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class TicketDto implements Serializable {
    private Integer id;
    private Integer amount;
    @Size(max = 255)
    private String description;
    @Size(max = 255)
    private String name;
    @NotNull private Integer eventId;
}