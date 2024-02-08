package ch.bbt.uek223.ticketshop.event;


import ch.bbt.uek223.ticketshop.event.dto.EventDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public EventDto create(EventDto expectedEventDTO) {
        return null;
    }

    public EventDto update(EventDto expectedEventDTO, int i) {
        return null;
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
