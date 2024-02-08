package ch.bbt.uek223.ticketshop.ticket;

import ch.bbt.uek223.ticketshop.ticket.dto.TicketDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TickerMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, TickerMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    public TicketDto create(TicketDto expectedTicketDTO) {
        return null;
    }

    public TicketDto findById(int i) {
        Ticket ticket = ticketRepository.findById(i).orElseThrow(EntityNotFoundException::new);
        return ticketMapper.toDto(ticket);
    }

    public List<TicketDto> findAll() {
        return ticketRepository.findAll().stream().map(ticketMapper::toDto).toList();
    }

    public TicketDto update(TicketDto changingTicketDTO, int i) {
        return null;
    }

    public List<TicketDto> buy(List<TicketDto> changingTicketDTOs)  {
        return null;
    }

    public void deleteById(int i) {
        ticketRepository.deleteById(i);
    }

    public List<TicketDto> findAllUnsold() {
        return ticketRepository.findAllUnsold().stream().map(ticketMapper::toDto).toList();
    }

    // Properties, Controller mit Injection, weitere Methoden, etc.
    // ...


//    public TicketDTO update(TicketDTO ticketDTO) {
//        // Lade das passende Ticket aus der Datenbank, rufe die Methode mergeTicket auf und speichere das Ticket anschliessend wieder.
//        // ...
//    }

//    public List<TicketDTO> buy(List<TicketDTO> ticketDTOs) {
//        // Erstelle eine Liste mit den IDs zu den Tickets aus ticketDTOs.
//        // ...
//
//        // Konvertiere die TicketDTOs aus ticketDTOs mit dem Mapper zu normalen Ticket Entities.
//        // ...
//
//        // Lies alle Tickets aus der Datenbank.
//        // ...
//
//        // Überprüfe hier, dass alle zu kaufenden Tickets auch in der Datenbank gefunden wurden. Falls nicht: Wirf eine EntityNotFoundExpception.
//        // ...
//
//        // Aufruf von mergeTicket
//        // ...
//
//        // Speichere die Ticket Entities mithilfe des Repositories.
//        // ...
//
//        // Konvertiere die Ticket Entities wieder in DTOs und returne sie.
//        // ...
//    }

    /**
     * Finde die Struktur für diese Methode selber heraus. Als Hilfe dienen dir die folgenden Eigenschaften, welche sie erfüllen muss:
     * 1) Die Anzahl an neu verfügbaren Tickets soll auf das Ticket aus der Datenbank gesetzt werden.
     * 2) Der neue Name soll übernommen werden, jedoch nur falls der Name nicht null ist.
     * 3) Das neue Event soll übernommen werden, jedoch nur falls das Event nicht null ist.
     * 4) Sollte die Anzahl an neu verfügbaren Tickets unter 0 sein, muss eine DataIntegrityViolation geworfen werden.
     *
     * @param existing Das bestehende Ticket, welches aus der Datenbank stammen sollte
     * @param changing Die Ticketentity, welche die neuen Angaben enthält, die auf das DB-Ticket gesetzt werden sollen.
     */
//    private void mergeTicket(Ticket existing, Ticket changing) {
//        // ...
//    }


//    private void mergeTickets(List<Ticket> existingTickets, List<Ticket> changingTickets) {
//        for (Ticket changingTicket : changingTickets) {
//            // Finde zum changingTicket die passende Entity aus der Datenbank
//            // ...
//
//            // In einem changingTicket befindet sich zur Zeit die Anzahl an Tickets, welche gekauft werden soll.
//            // Für die Methode mergeTicket muss jedoch die Anzahl darinstehen, welche nach dem Kauf noch verfügbar sein soll.
//            // ...
//
//            // Aufruf von mergeTicket
//            // ...
//        }
//    }
}
