package ch.bbt.uek223.ticketshop.mapper;


import ch.bbt.uek223.ticketshop.event.Event;
import ch.bbt.uek223.ticketshop.event.EventMapper;
import ch.bbt.uek223.ticketshop.event.dto.EventDto;
import ch.bbt.uek223.ticketshop.person.Person;
import ch.bbt.uek223.ticketshop.ticket.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class EventMapperTest {
    @Spy
    private EventMapper eventMapper;


    @Test
    void testFromDTO() {
        // Given
        EventDto eventDTO = new EventDto();
        eventDTO.setId(1);
        eventDTO.setDate(new Date(1));
        eventDTO.setDescription("Test event");
        eventDTO.setName("Test");
        eventDTO.setOwnerId(1);
        List<Integer> ticketIds = new ArrayList<>();
        ticketIds.add(1);
        ticketIds.add(2);
        eventDTO.setTicketIds(ticketIds);
        // When
        Event event = eventMapper.toEntity(eventDTO);
        // Then
        assertEquals(eventDTO.getId(), event.getId());
        assertEquals(eventDTO.getDate(), event.getDate());
        assertEquals(eventDTO.getDescription(), event.getDescription());
        assertEquals(eventDTO.getName(), event.getName());
        assertEquals(eventDTO.getTicketIds().size(), event.getTickets().size());
    }

    @Test
    void testToDTO() {
        // Given
        Event event = new Event();
        event.setId(1);
        event.setDate(new Date(1));
        event.setDescription("Test event");
        event.setName("Test");
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket1 = new Ticket();
        ticket1.setId(1);
        tickets.add(ticket1);
        Ticket ticket2 = new Ticket();
        ticket2.setId(2);
        tickets.add(ticket2);
        event.setTickets(tickets);
        Person owner = new Person();
        owner.setId(1);
        event.setOwner(owner);
        // When
        EventDto eventDTO = eventMapper.toDto(event);
        // Then
        assertEquals(event.getId(), eventDTO.getId());
        assertEquals(event.getDate(), eventDTO.getDate());
        assertEquals(event.getDescription(), eventDTO.getDescription());
        assertEquals(event.getName(), eventDTO.getName());
        assertEquals(event.getOwner().getId(), eventDTO.getOwnerId());
        assertEquals(event.getTickets().size(), eventDTO.getTicketIds().size());
    }
}