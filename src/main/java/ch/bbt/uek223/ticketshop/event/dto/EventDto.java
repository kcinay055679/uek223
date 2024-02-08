package ch.bbt.uek223.ticketshop.event.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * DTO for {@link ch.bbt.uek223.ticketshop.event.Event}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class EventDto implements Serializable {
    private Integer id;
    @NotNull private Date date;
    @Size(max = 255)
    private String description;
    @Size(max = 255)
    @NotBlank private String name;
    @NotNull private Integer ownerId;
    private List<Integer> ticketIds;
}