package ch.bbt.uek223.ticketshop.ticket;

import ch.bbt.uek223.ticketshop.event.EventController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(TicketController.PATH)

public class TicketController {
    public static final String PATH = "/ticket";
}
