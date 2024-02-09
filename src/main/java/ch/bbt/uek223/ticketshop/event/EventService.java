package ch.bbt.uek223.ticketshop.event;


import ch.bbt.uek223.ticketshop.Validator;
import ch.bbt.uek223.ticketshop.event.dto.EventDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService extends Validator<Event> {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public EventDto create(EventDto expectedEventDTO) {
        Event expectedEvent = eventMapper.toEntity(expectedEventDTO);
        Event actualEvent = eventRepository.save(expectedEvent);
        return eventMapper.toDto(actualEvent);
    }

    public EventDto update(EventDto expectedEventDTO, int i) {
        Event existingEvent = eventRepository.findById(i).orElseThrow(EntityNotFoundException::new);
        Event changingEvents = eventMapper.toEntity(expectedEventDTO);
        existingEvent.setName(changingEvents.getName());
        existingEvent.setDescription(changingEvents.getDescription());
        existingEvent.setDate(changingEvents.getDate());
        existingEvent.setTickets(changingEvents.getTickets());
        return eventMapper.toDto(eventRepository.save(existingEvent));
    }

    public List<EventDto> findAll() {
        return eventRepository.findAll().stream().map(eventMapper::toDto).toList();
    }

    public EventDto findById(int i) {
        return eventMapper.toDto(eventRepository.findById(i).orElseThrow(EntityNotFoundException::new));
    }

    public void deleteById(int i) {
        eventRepository.deleteById(i);
    }
}
