package ch.bbt.uek223.ticketshop.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private Date date;
    @Size(max = 255)
    private String description;
    @Size(max = 255)
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private Integer ownerId;
    private List<Integer> ticketIds;
}