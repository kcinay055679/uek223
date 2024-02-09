package ch.bbt.uek223.ticketshop.ticket;

import ch.bbt.uek223.ticketshop.Mapper;
import ch.bbt.uek223.ticketshop.event.Event;
import ch.bbt.uek223.ticketshop.ticket.dto.TicketDto;
import org.springframework.stereotype.Component;

@Component
public class TickerMapper implements Mapper<Ticket, TicketDto> {
    public TicketDto toDto(Ticket event) {
        return new TicketDto(event.getId(), event.getAmount(), event.getDescription(), event.getName(), event.getEvent().getId());
    }

    public Ticket toEntity(TicketDto eventDto) {
        return new Ticket().setId(eventDto.getId()).setDescription(eventDto.getDescription()).setName(eventDto.getName()).setAmount(eventDto.getAmountToBuy()).setEvent(new Event().setId(eventDto.getEventId()));
    }
}
