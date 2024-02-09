package ch.bbt.uek223.ticketshop.person;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(PersonController.PATH)
public class PersonController {
    public static final String PATH = "/person";
}
