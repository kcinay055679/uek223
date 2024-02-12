package ch.bbt.uek223.ticketshop.controller;

import ch.bbt.uek223.ticketshop.DataDTOUtil;
import ch.bbt.uek223.ticketshop.person.PersonController;
import ch.bbt.uek223.ticketshop.person.PersonService;
import ch.bbt.uek223.ticketshop.person.dto.PersonResponseDto;
import ch.bbt.uek223.ticketshop.security.AuthController;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
@EnableWebMvc
@AutoConfigureMockMvc(addFilters = false)
 class PersonRoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
     void checkPut_whenValidIdAndValidRole_thenIsOkAndPersonDTOIsReturned() throws Exception {
        PersonResponseDto expectedPersonResponseDTO = DataDTOUtil.getTestPersonResponseDTO();

        Mockito.when(personService.assignRole(eq(1), anyString())).thenReturn(List.of("USER", "ADMIN"));

        mockMvc.perform(put(PersonController.PATH + "/1/role/ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1]", is("ADMIN")));
    }

    @Test
     void checkPut_whenValidIdAndNotExistingRole_thenIsNotFound() throws Exception {
        Mockito.when(personService.assignRole(eq(1), anyString())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put(PersonController.PATH + "/1/role/NOT_EXISTING"))
                .andExpect(status().isNotFound());
    }

    @Test
     void checkDelete_whenValidIdAndValidRole_thenIsNoContent() throws Exception {
        mockMvc.perform(delete(PersonController.PATH + "/1/role/ADMIN"))
                .andExpect(status().isNoContent());
    }

    @Test
     void checkDelete_whenValidIdAndNotExistingRole_thenIsNotFound() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(personService).removeRole(eq(1), anyString());

        mockMvc.perform(delete(PersonController.PATH + "/1/role/NOT_EXISTING"))
                .andExpect(status().isNotFound());
    }
}
