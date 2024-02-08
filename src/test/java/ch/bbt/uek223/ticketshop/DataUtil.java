package ch.bbt.uek223.ticketshop;

import ch.bbt.uek223.ticketshop.event.Event;
import ch.bbt.uek223.ticketshop.person.Person;
import ch.bbt.uek223.ticketshop.ticket.Ticket;

public class DataUtil {
    public static Event getTestEvent() {
        return new Event();
    }

    public static Person getTestPerson() {
        return new Person();
    }

    public static Ticket getTestTicket() {
        return new Ticket();
    }
}
