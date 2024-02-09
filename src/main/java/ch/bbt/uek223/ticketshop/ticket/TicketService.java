package ch.bbt.uek223.ticketshop.ticket;

import ch.bbt.uek223.ticketshop.ticket.dto.TicketDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TickerMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, TickerMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    public TicketDto create(TicketDto expectedTicketDTO) {
        Ticket ticket = ticketMapper.toEntity(expectedTicketDTO);
        return ticketMapper.toDto(ticketRepository.save(ticket));
    }

    public TicketDto findById(int i) {
        return ticketMapper.toDto(findEntityById(i));
    }

    public Ticket findByIdForUpdates(int i) {
        return Optional.ofNullable(ticketRepository.findByIdForUpdate(i)).orElseThrow(() -> new EntityNotFoundException("Ticket with id " + i + " not found"));
    }

    public Ticket findEntityById(int i) {
        return ticketRepository.findById(i).orElseThrow(EntityNotFoundException::new);
    }

    public List<TicketDto> findAll() {
        return ticketRepository.findAll().stream().map(ticketMapper::toDto).toList();
    }

    public TicketDto update(TicketDto changingTicketDTO, Integer id) {
        // Lade das passende Ticket aus der Datenbank, rufe die Methode mergeTicket auf und speichere das Ticket anschliessend wieder.
//        // ...

        Ticket existingTicket = findByIdForUpdates(id);
        Ticket changingTicket = ticketMapper.toEntity(changingTicketDTO);
        mergeTicket(existingTicket, changingTicket);
        return ticketMapper.toDto(ticketRepository.save(existingTicket));
    }

    public void deleteById(int i) {
        ticketRepository.deleteById(i);
    }

    public List<TicketDto> findAllUnsold() {
        return ticketRepository.findAllUnsold().stream().map(ticketMapper::toDto).toList();
    }

    // Properties, Controller mit Injection, weitere Methoden, etc.
    // ...

    // Lade das passende Ticket aus der Datenbank, rufe die Methode mergeTicket auf und speichere das Ticket anschliessend wieder.
    @Transactional
    public TicketDto update(TicketDto ticketDTO) {
        Ticket changingTicket = ticketMapper.toEntity(ticketDTO);
        Ticket existingTicket = findEntityById(ticketDTO.getId());
        mergeTicket(existingTicket, changingTicket);
        return ticketMapper.toDto(ticketRepository.save(existingTicket));
    }

    @Transactional
    public List<TicketDto> buy(List<TicketDto> ticketDTOs) {
        List<Integer> ticketIds = ticketDTOs.stream().map(TicketDto::getId).toList();
        List <Ticket> existingTickets = ticketRepository.findAllByIdForUpdate(ticketIds);
        if(ticketIds.size() != existingTickets.size()) {
            throw new EntityNotFoundException("Not all tickets could be found");
        }

        List<Ticket> changingTickets = ticketDTOs.stream().map(ticketMapper::toEntity).toList();
        mergeTickets(existingTickets, changingTickets);

        return ticketRepository.saveAll(existingTickets).stream().map(ticketMapper::toDto).toList();
    }

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
    private void mergeTicket(Ticket existing, Ticket changing) {
        existing.setAmount(changing.getAmount());
        if(changing.getName() != null) {
            existing.setName(changing.getName());
        }
        if(changing.getEvent() != null) {
            existing.setEvent(changing.getEvent());
        }
        if(existing.getAmount() < 0) {
            throw new DataIntegrityViolationException("The amount of tickets must be at least 0");
        }
    }


    private void mergeTickets(List<Ticket> existingTickets, List<Ticket> changingTickets) {
        for (Ticket changingTicket : changingTickets) {
            // Finde zum changingTicket die passende Entity aus der Datenbank
            Ticket existingTicket = existingTickets.stream().filter(t -> t.getId().equals(changingTicket.getId())).findFirst().orElseThrow(() -> new EntityNotFoundException("Ticket with id " + changingTicket.getId() + " not found"));
            changingTicket.setAmount(existingTicket.getAmount() - changingTicket.getAmount());
            mergeTicket(existingTicket, changingTicket);
        }
    }
}
