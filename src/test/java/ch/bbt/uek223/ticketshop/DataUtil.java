package ch.bbt.uek223.ticketshop;



import ch.bbt.uek223.ticketshop.role.Role;
import ch.bbt.uek223.ticketshop.event.Event;
import ch.bbt.uek223.ticketshop.person.Person;
import ch.bbt.uek223.ticketshop.ticket.Ticket;

import java.sql.Date;
import java.util.*;

public class DataUtil {
    public static Person getTestPerson() {
        return getTestPersons().getFirst();
    }

    public static Person getTestPersonWithRole() {
        Person person = getTestPerson();
        person.setAssignedRoles(Set.of(getTestRole()));
        return person;
    }

    public static Role getTestRole() {
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");
        return role;
    }

    public static List<Person> getTestPersons() {
        List<Person> personList = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Person person = new Person();
            person.setId(i);
            person.setEmail("person" + i + "@foo.bar");
            person.setPassword("password" + i);
            person.setAssignedRoles(Set.of(getTestRole()));
            Event event = new Event();

            event.setId(i);
            event.setDate(Date.valueOf("2022-06-04"));
            event.setOwner(person);
            event.setName("Event" + i);
            event.setDescription("Description" + i);
            event.setTickets(new ArrayList<>());

            person.setEvents(new HashSet<>());
            person.getEvents().add(event);
            personList.add(person);
        }
        return personList;
    }

    public static Event getTestEvent() {
        return getTestEvents().get(0);
    }

    public static List<Event> getTestEvents() {
        List<Event> events = new ArrayList<>();
        List<Person> persons = getTestPersons();

        for (int i = 1; i <= 4; i++) {
            Event event = new Event();
            event.setId(i);
            event.setOwner(persons.get(i - 1));
            event.setName("Event" + i);
            event.setDescription("Description" + i);
            event.setDate(Date.valueOf("2022-06-04"));

            List<Ticket> ticketSet = new ArrayList<>();
            Ticket ticket = new Ticket();
            ticket.setId(i);
            ticket.setEvent(event);
            ticket.setName("Ticket" + i);
            ticket.setDescription("Description" + i);
            ticket.setAmount(i - 1);
            ticketSet.add(ticket);

            event.setTickets(ticketSet);

            events.add(event);
        }

        return events;
    }

    public static Ticket getTestTicket() {
        return getTestTickets().get(0);
    }

    public static List<Ticket> getTestTickets() {
        List<Ticket> tickets = new ArrayList<>();

        Event event = new Event();
        event.setId(1);
        event.setName("Event1");
        event.setDescription("Description1");
        event.setOwner(getTestPerson());

        for (int i = 1; i <= 4; i++) {
            Ticket ticket = new Ticket();
            ticket.setId(i);
            ticket.setName("Ticket" + i);
            ticket.setDescription("Description" + i);
            ticket.setEvent(event);
            ticket.setAmount(i);
            tickets.add(ticket);
        }

        return tickets;
    }
}
