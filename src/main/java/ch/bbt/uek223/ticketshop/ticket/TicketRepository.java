package ch.bbt.uek223.ticketshop.ticket;

import ch.bbt.uek223.ticketshop.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("SELECT t FROM Ticket t WHERE t.amount > 0")
    List<Ticket> findAllUnsold();
    @Query("SELECT t FROM Ticket t WHERE t.id = ?1")
    Ticket findByIdForUpdate(Integer id);
    @Query("SELECT t FROM Ticket t WHERE t.id = ?1")
    List<Ticket> findAllByIdForUpdate(Integer id);
}