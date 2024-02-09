package ch.bbt.uek223.ticketshop.mapper;

import ch.bbt.uek223.ticketshop.event.Event;
import ch.bbt.uek223.ticketshop.ticket.TickerMapper;
import ch.bbt.uek223.ticketshop.ticket.Ticket;
import ch.bbt.uek223.ticketshop.ticket.dto.TicketDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class TicketMapperTest {

    @Spy
    private TickerMapper ticketMapper;

    @Test
    void testToDTO() {
        // Given
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setName("Test Ticket");
        ticket.setDescription("This is a test ticket");
        ticket.setAmount(10);
        Event event = new Event();
        event.setId(101);
        ticket.setEvent(event);

        // When
        TicketDto ticketDTO = ticketMapper.toDto(ticket);

        // Then
        assertNotNull(ticketDTO);
        assertEquals(ticket.getId(), ticketDTO.getId());
        assertEquals(ticket.getName(), ticketDTO.getName());
        assertEquals(ticket.getDescription(), ticketDTO.getDescription());
        assertEquals(ticket.getAmount(), ticketDTO.getAmountToBuy());
        assertEquals(ticket.getEvent().getId(), ticketDTO.getEventId());
    }

    @Test
    void testFromDTO() {
        // Given
        TicketDto ticketDTO = new TicketDto();
        ticketDTO.setId(1);
        ticketDTO.setName("Test Ticket DTO");
        ticketDTO.setDescription("This is a test ticket DTO");
        ticketDTO.setAmountToBuy(5);
        ticketDTO.setEventId(201);

        // When
        Ticket ticket = ticketMapper.toEntity(ticketDTO);

        // Then
        assertNotNull(ticket);
        assertEquals(ticketDTO.getId(), ticket.getId());
        assertEquals(ticketDTO.getName(), ticket.getName());
        assertEquals(ticketDTO.getDescription(), ticket.getDescription());
        assertEquals(ticketDTO.getAmountToBuy(), ticket.getAmount());
    }
}

