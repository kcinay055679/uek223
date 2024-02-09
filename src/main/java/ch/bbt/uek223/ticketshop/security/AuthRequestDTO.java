package ch.bbt.uek223.ticketshop.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthRequestDTO {
    @NotBlank(message = "email must not be empty")
    private String email;

    @NotBlank(message = "password must not be empty")
    private String password;
}
