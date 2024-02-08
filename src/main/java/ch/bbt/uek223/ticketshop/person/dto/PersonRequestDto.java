package ch.bbt.uek223.ticketshop.person.dto;

import ch.bbt.uek223.ticketshop.person.Person;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link Person}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PersonRequestDto extends PersonResponseDto implements Serializable {
    @Size(max = 255)
    private String password;
}