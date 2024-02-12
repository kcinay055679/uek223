package ch.bbt.uek223.ticketshop.controller;

import ch.bbt.uek223.ticketshop.DataDTOUtil;
import ch.bbt.uek223.ticketshop.DataUtil;
import ch.bbt.uek223.ticketshop.person.PersonController;
import ch.bbt.uek223.ticketshop.person.PersonService;
import ch.bbt.uek223.ticketshop.security.AuthController;
import ch.bbt.uek223.ticketshop.security.SecurityConfiguration;
import ch.bbt.uek223.ticketshop.ticket.TicketController;
import ch.bbt.uek223.ticketshop.ticket.TicketService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TicketController.class)
@EnableWebMvc
@AutoConfigureMockMvc(addFilters = false)
class TicketControllerTest {


    @MockBean
    private TicketService ticketService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void checkFindById_whenValidRequestDTO_thenIsOk() throws Exception {
        Mockito.when(ticketService.findById(1)).thenReturn(DataDTOUtil.getTestTicketDTO());

        mockMvc.perform(get(TicketController.PATH + "/1")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Ticket1")));
    }

    @Test
    @Order(2)
    void checkCreate_whenValidRequestDTO_thenIsOk() throws Exception {
        Mockito.when(ticketService.create(any())).thenReturn(DataDTOUtil.getTestTicketDTO());

        mockMvc.perform(post(TicketController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Ticket1\", \"eventId\": 1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Ticket1")));
    }
}
