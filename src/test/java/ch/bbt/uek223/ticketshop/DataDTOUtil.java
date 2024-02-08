package ch.bbt.uek223.ticketshop;

import ch.bbt.uek223.ticketshop.event.dto.EventDto;
import ch.bbt.uek223.ticketshop.person.dto.PersonRequestDto;
import ch.bbt.uek223.ticketshop.person.dto.PersonResponseDto;
import ch.bbt.uek223.ticketshop.ticket.dto.TicketDto;

import java.sql.Date;
import java.util.*;

public class DataDTOUtil {
    public static final String JSON_ALL_EVENT_DTOS = "[{\"id\":1,\"name\":\"Event1\",\"ownerId\":1,\"ticketIds\":[1],\"date\":\"2022-06-04\",\"description\":\"Description1\"}," +
            "{\"id\":2,\"name\":\"Event2\",\"ownerId\":2,\"ticketIds\":[2],\"date\":\"2022-06-04\",\"description\":\"Description2\"}," +
            "{\"id\":3,\"name\":\"Event3\",\"ownerId\":3,\"ticketIds\":[3],\"date\":\"2022-06-04\",\"description\":\"Description3\"}," +
            "{\"id\":4,\"name\":\"Event4\",\"ownerId\":4,\"ticketIds\":[4],\"date\":\"2022-06-04\",\"description\":\"Description4\"}]";
    public static final String JSON_ALL_TICKET_DTOS = "[{\"id\":1,\"name\":\"Ticket1\",\"description\":\"Description1\",\"eventId\":1,\"amountToBuy\":1}," +
            "{\"id\":2,\"name\":\"Ticket2\",\"description\":\"Description2\",\"eventId\":1,\"amountToBuy\":2}," +
            "{\"id\":3,\"name\":\"Ticket3\",\"description\":\"Description3\",\"eventId\":1,\"amountToBuy\":3}," +
            "{\"id\":4,\"name\":\"Ticket4\",\"description\":\"Description4\",\"eventId\":1,\"amountToBuy\":4}]";

    public static PersonResponseDto getTestPersonResponseDTO() {
        return getTestPersonResponseDTOs().get(0);
    }

    public static List<PersonResponseDto> getTestPersonResponseDTOs() {
        List<PersonResponseDto> personList = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            PersonResponseDto person = new PersonResponseDto();
            person.setId(i);
            person.setEmail("person" + i + "@foo.bar");
            person.setEventIds(new ArrayList<>());
            person.getEventIds().add(i);
            personList.add(person);
        }
        return personList;
    }

    public static PersonRequestDto getTestPersonRequestDTO() {
        PersonRequestDto personRequestDTO = new PersonRequestDto();
        personRequestDTO.setId(1);
        personRequestDTO.setEmail("person1@foo.bar");
        personRequestDTO.setPassword("password1");
        personRequestDTO.setEventIds(new ArrayList<>());
        personRequestDTO.getEventIds().add(1);

        return personRequestDTO;
    }

    public static EventDto getTestEventDTO(Integer id) {
        return getTestEventDTOs().get(id);
    }

    public static List<EventDto> getTestEventDTOs() {
        List<EventDto> eventDTOs = new ArrayList<>();
        List<PersonResponseDto> testPersonResponseDTOS = getTestPersonResponseDTOs();

        for (int i = 1; i <= 4; i++) {
            EventDto eventDTO = new EventDto(i, Date.valueOf("2022-06-04"),"Description" + i, "Event" + i,testPersonResponseDTOS.get(i - 1).getId(), List.of(i));
            eventDTOs.add(eventDTO);
        }

        return eventDTOs;
    }

    public static TicketDto getTestTicketDTO() {
        return getTestTicketDTOs().get(0);
    }

    public static TicketDto getInvalidTestTicketDTO() {
        return new TicketDto(0, 0 , "", "", 0);
    }

    public static List<TicketDto> getTestTicketDTOs() {
        List<TicketDto> tickets = new ArrayList<>();

        int eventId = 1;

        for (int i = 1; i <= 4; i++) {
            TicketDto ticket = new TicketDto(i, i,  "Description" + i, "Ticket" + i, eventId);
            tickets.add(ticket);
        }

        return tickets;
    }
}
