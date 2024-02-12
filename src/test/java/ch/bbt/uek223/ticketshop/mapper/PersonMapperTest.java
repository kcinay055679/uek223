package ch.bbt.uek223.ticketshop.mapper;


import ch.bbt.uek223.ticketshop.event.Event;
import ch.bbt.uek223.ticketshop.event.dto.EventDto;
import ch.bbt.uek223.ticketshop.person.Person;
import ch.bbt.uek223.ticketshop.person.PersonMapper;
import ch.bbt.uek223.ticketshop.person.dto.PersonRequestDto;
import ch.bbt.uek223.ticketshop.person.dto.PersonResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class PersonMapperTest {

    @Spy
    private PersonMapper personMapper;


    @Test
    void testFromDTO() {
        // Given
        PersonRequestDto personRequestDTO = new PersonRequestDto();
        personRequestDTO.setPassword("testPassword");
        personRequestDTO.setEventIds(List.of(1, 2));
        personRequestDTO.setEmail("test@test.ch");

        // When
        Person person = personMapper.toEntity(personRequestDTO);

        // Then
        assertEquals(personRequestDTO.getPassword(), person.getPassword());
        assertEquals(personRequestDTO.getEmail(), person.getEmail());
        assertEquals(personRequestDTO.getEventIds(), person.getEvents().stream().map(Event::getId).toList());
    }

    @Test
    void testToDTO() {
        // Given
        Person person = new Person();
        person.setId(1);
        person.setEmail("test@example.com");

        Set<Event> events = new HashSet<>();
        Event event1 = new Event();
        event1.setId(101);
        events.add(event1);

        Event event2 = new Event();
        event2.setId(102);
        events.add(event2);

        person.setEvents(events);

        // When
        PersonResponseDto personResponseDTO = personMapper.toDto(person);

        // Then
        assertEquals(person.getId(), personResponseDTO.getId());
        assertEquals(person.getEmail(), personResponseDTO.getEmail());
        assertEquals(person.getEvents().size(), personResponseDTO.getEventIds().size());
    }
}

