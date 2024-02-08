package ch.bbt.uek223.ticketshop.person;

import ch.bbt.uek223.ticketshop.Mapper;
import ch.bbt.uek223.ticketshop.event.Event;
import ch.bbt.uek223.ticketshop.person.dto.PersonRequestDto;
import ch.bbt.uek223.ticketshop.person.dto.PersonResponseDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PersonMapper implements Mapper<Person, PersonResponseDto> {
    public PersonResponseDto toDto(Person person) {
        return new PersonResponseDto(person.getId(), person.getEmail(), person.getEvents().stream().map(Event::getId).collect(Collectors.toSet()));
    }

    public Person toEntity(PersonResponseDto personResponseDto) {
        return toEntity((PersonRequestDto)personResponseDto);
    }

    public Person toEntity(PersonRequestDto eventDto) {
        return new Person().setId(eventDto.getId()).setEmail(eventDto.getEmail()).setPassword(eventDto.getPassword()).setEvents(eventDto.getEventIds().stream().map(id -> new Event().setId(id)).collect(Collectors.toSet()));
    }
}
