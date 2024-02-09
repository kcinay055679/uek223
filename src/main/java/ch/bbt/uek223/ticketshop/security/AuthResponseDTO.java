package ch.bbt.uek223.ticketshop.security;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private Integer id;
    private String email;
}
