package ch.bbt.uek223.ticketshop.controller;


import ch.bbt.uek223.ticketshop.DataDTOUtil;
import ch.bbt.uek223.ticketshop.DataUtil;
import ch.bbt.uek223.ticketshop.person.PersonService;
import ch.bbt.uek223.ticketshop.person.dto.PersonResponseDto;
import ch.bbt.uek223.ticketshop.role.Role;
import ch.bbt.uek223.ticketshop.role.RoleService;
import ch.bbt.uek223.ticketshop.security.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Order(43)
@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc
@Import(SecurityConfiguration.class)
public class AuthControllerTest {
    @MockBean
    AuthenticationManager authenticationManager;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;

    @MockBean
    private RoleService roleService;

    @Test
    @Order(1)
    public void checkSignUp_whenInvalidAuthRequestDTO_thenIsBadRequest() throws Exception {
        mockMvc.perform(post(AuthController.PATH + "/signup")
                        .contentType("application/json")
                        .content("{\"email\":\"person1@foo.bar\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(1)
    public void checkSignUp_withValidAuthRequestDTO_thenIsCreated() throws Exception {
        PersonResponseDto expectedPersonResponseDTO = new PersonResponseDto();
        expectedPersonResponseDTO.setId(1);
        expectedPersonResponseDTO.setEmail("person1@foo.bar");

        Mockito.when(personService.existsByEmail(anyString())).thenReturn(false);
        Mockito.when(personService.create(any(AuthRequestDTO.class))).thenReturn(expectedPersonResponseDTO);

        mockMvc.perform(post(AuthController.PATH + "/signup")
                        .contentType("application/json")
                        .content("{\"email\":\"person1@foo.bar\",\"password\":\"password1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("person1@foo.bar")));

    }

    @Test
    @Order(1)
    public void checkSignUp_whenEmailAlreadyInUse_thenIsConflict() throws Exception {
        Mockito.when(personService.existsByEmail(anyString())).thenReturn(true);

        mockMvc.perform(post(AuthController.PATH + "/signup")
                        .contentType("application/json")
                        .content("{\"email\":\"person1@foo.bar\",\"password\":\"password1\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(2)
    public void checkSignIn_whenInvalidAuthRequestDTO_thenIsBadRequest() throws Exception {
        mockMvc.perform(post(AuthController.PATH + "/signup")
                        .contentType("application/json")
                        .content("{\"email\":\"person1@foo.bar\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    public void checkSignIn_whenInvalidCredentials_thenIsUnauthorized() throws Exception {
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        mockMvc.perform(post(AuthController.PATH + "/signin")
                        .contentType("application/json")
                        .content("{\"email\":\"person1@foo.bar\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(2)
    public void checkSignIn_whenValidCredentials_thenIsOk() throws Exception {
        List<Role> returnedRoles = new ArrayList<>();
        returnedRoles.add(DataUtil.getTestRole());

        AuthenticationMock returnedAuthentication = new AuthenticationMock();
        returnedAuthentication.setPrincipal(new User("person1@foo.bar", "password1", new ArrayList<>()));

        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(returnedAuthentication);

        Mockito.when(roleService.getRolesByUserEmail(anyString())).thenReturn(returnedRoles);
        Mockito.when(personService.findByEmail(anyString())).thenReturn(DataDTOUtil.getTestPersonResponseDTO());

        mockMvc.perform(post(AuthController.PATH + "/signin")
                        .contentType("application/json")
                        .content("{\"email\":\"person1@foo.bar\",\"password\": \"password1\"}"))
                .andExpect(status().isOk());
    }

    private static class AuthenticationMock implements Authentication {
        private Object principal;

        // not implemented
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("MOCKROLE"));
            return authorities;
        }

        @Override
        public Object getCredentials() {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public Object getDetails() {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }

        public void setPrincipal(Object principal) {
            this.principal = principal;
        }

        @Override
        public boolean isAuthenticated() {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public String getName() {
            return "person1@foo.bar";
        }
    }
}
