package ch.bbt.uek223.ticketshop.event;

import ch.bbt.uek223.ticketshop.Mapper;
import ch.bbt.uek223.ticketshop.event.dto.EventDto;
import ch.bbt.uek223.ticketshop.ticket.Ticket;
import org.springframework.stereotype.Component;


@Component
public class EventMapper implements Mapper<Event, EventDto> {
    public EventDto toDto(Event event) {
        return new EventDto(event.getId(), event.getDate(), event.getDescription(), event.getName(), event.getOwner().getId(), event.getTickets().stream().map(Ticket::getId).toList());
    }

    public Event toEntity(EventDto eventDto) {
        return new Event().setId(eventDto.getId()).setDescription(eventDto.getDescription()).setName(eventDto.getName()).setDate(eventDto.getDate()).setTickets(eventDto.getTicketIds().stream().map(id -> new Ticket().setId(id)).toList());
    }
}
