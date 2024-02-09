package ch.bbt.uek223.ticketshop.controller;

import ch.bbt.uek223.ticketshop.DataDTOUtil;
import ch.bbt.uek223.ticketshop.event.EventController;
import ch.bbt.uek223.ticketshop.event.EventService;
import ch.bbt.uek223.ticketshop.event.dto.EventDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.TimeZone;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Order(41)
@WebMvcTest(controllers = EventController.class)
@AutoConfigureMockMvc(addFilters = false)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @BeforeEach
    void prepare() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    @Order(1)
    void checkGet_whenNoParam_thenAllEventsAreReturned() throws Exception {
        Mockito.when(eventService.findAll()).thenReturn(DataDTOUtil.getTestEventDTOs());

        mockMvc.perform(get(EventController.PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(DataDTOUtil.JSON_ALL_EVENT_DTOS));
    }

    @Test
    @Order(2)
    void checkFindById_whenValidId_thenEventIsReturned() throws Exception {
        EventDto expected = DataDTOUtil.getTestEventDTO(0);
        Mockito.when(eventService.findById(eq(1))).thenReturn(expected);

        mockMvc.perform(get(EventController.PATH + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Event1")));
    }

    @Test
    @Order(2)
    void checkFindById_whenInvalidId_thenIsNotFound() throws Exception {
        Mockito.when(eventService.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get(EventController.PATH + "/" + 0))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void checkPatch_whenValidEvent_thenIsOk() throws Exception {
        String newName = "NewEventName";

        EventDto expected = DataDTOUtil.getTestEventDTO(0);
        expected.setName(newName);

        Mockito.when(eventService.update(any(EventDto.class), eq(1))).thenReturn(expected);

        mockMvc.perform(patch(EventController.PATH + "/" + 1)
                        .contentType("application/json")
                        .content("{\"id\":1,\"name\":\"NewEventName\",\"date\":\"2022-06-04\",\"ownerId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newName)));

    }

    @Test
    @Order(4)
    void checkPatch_whenInvalidEvent_thenIsBadRequest() throws Exception {
        String newName = "";

        EventDto expected = DataDTOUtil.getTestEventDTO(0);
        expected.setName(newName);

        mockMvc.perform(patch(EventController.PATH + "/" + 1)
                        .contentType("application/json")
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(4)
    void checkPatch_whenNameExists_thenIsConflict() throws Exception {
        String existingName = "Event1";

        Mockito.when(eventService.update(any(EventDto.class), eq(2))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(patch(EventController.PATH + "/" + 2)
                        .contentType("application/json")
                        .content("{\"name\":\"" + existingName + "\", \"date\": \"2022-06-04\", \"ownerId\": 1}"))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    void checkPost_whenValidEvent_thenIsCreated() throws Exception {
        EventDto createdEvent = DataDTOUtil.getTestEventDTO(0);

        Mockito.when(eventService.create(any(EventDto.class))).thenReturn(createdEvent);

        mockMvc.perform(post(EventController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Event1\",\"date\":\"2022-06-04\",\"description\":\"Description1\",\"ownerId\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    void checkPost_whenInvalidEvent_thenIsBadRequest() throws Exception {
        Mockito.when(eventService.create(any(EventDto.class))).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(post(EventController.PATH)
                        .contentType("application/json")
                        .content("{\"date\":\"2022-06-04\",\"description\":\"Description1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void checkPost_whenOwnerIdNotSet_thenIsBadRequest() throws Exception {
        mockMvc.perform(post(EventController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Event1\",\"date\":\"2022-06-04\",\"description\":\"Description1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void checkPost_whenInvalidOwnerId_thenIsConflict() throws Exception {
        Mockito.when(eventService.create(any(EventDto.class))).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(post(EventController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Event1\",\"date\":\"2022-06-04\",\"description\":\"Description1\",\"ownerId\":0}"))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(5)
    void checkDelete_whenValidId_thenIsNoContent() throws Exception {
        mockMvc.perform(delete(EventController.PATH + "/" + 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(5)
    void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        Mockito.doThrow(EmptyResultDataAccessException.class).when(eventService).deleteById(0);

        mockMvc.perform(delete(EventController.PATH + "/" + 0))
                .andExpect(status().isNotFound());
    }
}
