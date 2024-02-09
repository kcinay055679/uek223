package ch.bbt.uek223.ticketshop.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class JwtResponseDTO extends AuthResponseDTO {
    private final Collection<? extends GrantedAuthority> authorities;
    private String accessToken;
    private String type = "Bearer";

    public JwtResponseDTO(String accessToken, Integer id, String email, Collection<? extends GrantedAuthority> authorities) {
        super(id, email);
        this.accessToken = accessToken;
        this.authorities = authorities;
    }
}
