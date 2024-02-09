package ch.bbt.uek223.ticketshop.ticket;

import ch.bbt.uek223.ticketshop.ticket.Ticket;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("SELECT t FROM Ticket t WHERE t.amount > 0")
    List<Ticket> findAllUnsold();
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Ticket t WHERE t.id = :id")
    Ticket findByIdForUpdate(Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Ticket t WHERE t.id IN :ids")
    List<Ticket> findAllByIdForUpdate(List<Integer> ids);
}