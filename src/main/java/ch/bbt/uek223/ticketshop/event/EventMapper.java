package ch.bbt.uek223.ticketshop.event;

import ch.bbt.uek223.ticketshop.Mapper;
import ch.bbt.uek223.ticketshop.ticket.Ticket;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EventMapper implements Mapper<Event, EventDto> {
    public EventDto toDto(Event event) {
        return new EventDto(event.getId(), event.getDate(), event.getDescription(), event.getName(), event.getOwner().getId(), event.getTickets().stream().map(Ticket::getId).collect(Collectors.toSet()));
    }

    public Event toEntity(EventDto eventDto) {
        return new Event().setId(eventDto.id()).setDescription(eventDto.description()).setName(eventDto.name()).setDate(eventDto.date()).setTickets(eventDto.ticketIds().stream().map(id -> new Ticket().setId(id)).collect(Collectors.toSet()));
    }
}
