package ch.bbt.uek223.ticketshop.service;

import ch.bbt.uek223.ticketshop.DataDTOUtil;
import ch.bbt.uek223.ticketshop.DataUtil;
import ch.bbt.uek223.ticketshop.event.*;
import ch.bbt.uek223.ticketshop.event.dto.EventDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.TransientPropertyValueException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@Order(31)
@ExtendWith(SpringExtension.class)
 class EventServiceTest {
    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Test
    @Order(3)
     void checkCreate_whenValidEventDTO_thenEventDTOIsReturned() {
        EventDto expectedEventDTO = DataDTOUtil.getTestEventDTO(0);

        Event expectedEvent = DataUtil.getTestEvent();

        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(expectedEvent);

        EventDto actualEventDTO = eventService.create(expectedEventDTO);

        assertEquals(expectedEventDTO.getId(), actualEventDTO.getId());
        assertEquals(expectedEventDTO.getName(), actualEventDTO.getName());
        assertEquals(expectedEventDTO.getDescription(), actualEventDTO.getDescription());
        assertEquals(expectedEventDTO.getDate(), actualEventDTO.getDate());
        assertEquals(expectedEventDTO.getOwnerId(), actualEventDTO.getOwnerId());
        assertEquals(expectedEventDTO.getTicketIds().size(), actualEventDTO.getTicketIds().size());

        for (int i = 0; i < expectedEventDTO.getTicketIds().size(); i++) {
            assertEquals(expectedEventDTO.getTicketIds().get(i), actualEventDTO.getTicketIds().get(i));
        }
    }

    @Test
    @Order(3)
     void checkCreate_whenInvalidOwnerId_thenConstraintViolation() {
        EventDto failingEventDTO = DataDTOUtil.getTestEventDTO(0);

        Mockito.when(eventRepository.save(any(Event.class))).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> eventService.create(failingEventDTO));
    }


    @Test
    @Order(3)
     void checkCreate_whenNoOwnerId_thenTransientPropertyValueException() {
        EventDto failingEventDTO = DataDTOUtil.getTestEventDTO(0);

        Mockito.when(eventRepository.save(any(Event.class))).thenThrow(TransientPropertyValueException.class);

        assertThrows(TransientPropertyValueException.class, () -> eventService.create(failingEventDTO));
    }

    @Test
    @Order(4)
     void checkUpdate_whenValidEventDTO_thenEventDTOIsReturned() {
        String newName = "NewEventName";

        EventDto expectedEventDTO = DataDTOUtil.getTestEventDTO(0);
        expectedEventDTO.setName(newName);

        Event unchangedEvent = DataUtil.getTestEvent();

        Event expectedEvent = DataUtil.getTestEvent();
        expectedEvent.setName(newName);

        Mockito.when(eventRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(eventRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(eventRepository.findById(eq(1))).thenReturn(Optional.of(unchangedEvent));
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(expectedEvent);

        EventDto actualEventDTO = eventService.update(expectedEventDTO, 1);

        assertEquals(expectedEventDTO.getName(), actualEventDTO.getName());
    }

    @Test
    @Order(4)
     void checkUpdate_whenInvalidOwnerId_thenDataIntegrityViolation() {
        EventDto failingEventDTO = DataDTOUtil.getTestEventDTO(0);
        failingEventDTO.setOwnerId(0);

        Event expectedEvent = DataUtil.getTestEvent();

        Mockito.when(eventRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(eventRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(eventRepository.findById(eq(1))).thenReturn(Optional.of(expectedEvent));

        Mockito.when(eventRepository.save(any(Event.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> eventService.update(failingEventDTO, 1));
    }

    @Test
    @Order(1)
     void checkFindAll_whenEventsExisting_thenReturnEventDTOList() {
        List<EventDto> expectedEventDTOs = DataDTOUtil.getTestEventDTOs();

        List<Event> expectedEvents = DataUtil.getTestEvents();

        Mockito.when(eventRepository.findAll()).thenReturn(expectedEvents);

        List<EventDto> actualEventDTOs = eventService.findAll();

        assertEquals(expectedEventDTOs.size(), actualEventDTOs.size());

        for (int i = 0; i < expectedEventDTOs.size(); i++) {
            EventDto expectedEventDTO = expectedEventDTOs.get(i);
            EventDto actualEventDTO = actualEventDTOs.get(i);

            assertEquals(expectedEventDTO.getId(), actualEventDTO.getId());
            assertEquals(expectedEventDTO.getName(), actualEventDTO.getName());
            assertEquals(expectedEventDTO.getDescription(), actualEventDTO.getDescription());
            assertEquals(expectedEventDTO.getDate(), actualEventDTO.getDate());
            assertEquals(expectedEventDTO.getOwnerId(), actualEventDTO.getOwnerId());
            assertEquals(expectedEventDTO.getTicketIds().size(), actualEventDTO.getTicketIds().size());
        }
    }

    @Test
    @Order(2)
     void checkFindById_whenValidId_thenEventDTOIsReturned() {
        EventDto expectedEventDTO = DataDTOUtil.getTestEventDTO(0);

        Event expectedEvent = DataUtil.getTestEvent();

        Mockito.when(eventRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(eventRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(eventRepository.findById(eq(1))).thenReturn(Optional.of(expectedEvent));

        EventDto actualEventDTO = eventService.findById(1);

        assertEquals(expectedEventDTO.getId(), actualEventDTO.getId());
        assertEquals(expectedEventDTO.getName(), actualEventDTO.getName());
        assertEquals(expectedEventDTO.getDescription(), actualEventDTO.getDescription());
        assertEquals(expectedEventDTO.getDate(), actualEventDTO.getDate());
        assertEquals(expectedEventDTO.getOwnerId(), actualEventDTO.getOwnerId());
        assertEquals(expectedEventDTO.getTicketIds().size(), actualEventDTO.getTicketIds().size());
    }

    @Test
    @Order(2)
     void checkFindById_whenInvalidId_thenEntityNotFound() {

        Mockito.when(eventRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(eventRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(eventRepository.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> eventService.findById(0));
    }

    @Test
    @Order(5)
     void checkDeleteById_whenInvalidId_thenEmptyResultDataAccess() {
        Mockito.doThrow(EmptyResultDataAccessException.class).when(eventRepository).deleteById(eq(0));

        assertThrows(EmptyResultDataAccessException.class, () -> eventService.deleteById(0));
    }
}
