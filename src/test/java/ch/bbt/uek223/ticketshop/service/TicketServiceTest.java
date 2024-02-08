package ch.bbt.uek223.ticketshop.service;

import ch.bbt.uek223.ticketshop.DataDTOUtil;
import ch.bbt.uek223.ticketshop.DataUtil;
import ch.bbt.uek223.ticketshop.ticket.*;
import ch.bbt.uek223.ticketshop.ticket.dto.TicketDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@Order(32)
@ExtendWith(SpringExtension.class)
 class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;


    private TickerMapper ticketMapper;

    public TicketServiceTest(TickerMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    @Test
    @Order(4)
     void checkCreateTicket_whenValidTicket_thenReturnTicketDTO() {
        TicketDto expectedTicketDTO = DataDTOUtil.getTestTicketDTO();
        Ticket expectedTicket = DataUtil.getTestTicket();

        Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(expectedTicket);

        TicketDto actual = ticketService.create(expectedTicketDTO);

        assertEquals(expectedTicketDTO.getId(), actual.getId());
        assertEquals(expectedTicketDTO.getName(), actual.getName());
        assertEquals(expectedTicketDTO.getAmount(), actual.getAmount());
        assertEquals(expectedTicketDTO.getEventId(), actual.getEventId());
    }

    @Test
    @Order(4)
     void checkCreateTicket_whenInvalidTicket_thenThrowConstraintViolation() {
        TicketDto failingTicketDTO = DataDTOUtil.getTestTicketDTO();
        failingTicketDTO.setEventId(0);

        Mockito.when(ticketRepository.save(any(Ticket.class))).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> ticketService.create(failingTicketDTO));
    }

    @Test
    @Order(4)
     void checkCreate_whenValidId_thenTicketDTOIsReturned() {
        TicketDto expectedTicketDTO = DataDTOUtil.getTestTicketDTO();
        Ticket expectedTicket = DataUtil.getTestTicket();

        Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(expectedTicket);

        TicketDto actualTicketDTO = ticketService.create(expectedTicketDTO);

        assertEquals(expectedTicketDTO.getId(), actualTicketDTO.getId());
        assertEquals(expectedTicketDTO.getName(), actualTicketDTO.getName());
        assertEquals(expectedTicketDTO.getAmount(), actualTicketDTO.getAmount());
        assertEquals(expectedTicketDTO.getEventId(), actualTicketDTO.getEventId());
    }

    @Test
    @Order(3)
     void checkFindById_whenValidId_thenTicketDTOIsReturned() {
        Ticket expected = DataUtil.getTestTicket();
        TicketDto expectedTicketDTO = DataDTOUtil.getTestTicketDTO();

        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(ticketRepository.findById(1)).thenReturn(Optional.of(expected));

        TicketDto actualTicketDTO = ticketService.findById(1);

        assertEquals(expectedTicketDTO.getId(), actualTicketDTO.getId());
        assertEquals(expectedTicketDTO.getName(), actualTicketDTO.getName());
        assertEquals(expectedTicketDTO.getAmount(), actualTicketDTO.getAmount());
        assertEquals(expectedTicketDTO.getEventId(), actualTicketDTO.getEventId());
    }

    @Test
    @Order(3)
     void checkFindById_whenInvalidId_thenThrowsEntityNotFound() {

        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(ticketRepository.findById(0)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> ticketService.findById(0));
    }

    @Test
    @Order(1)
     void checkFindAll_whenExistingTickets_thenTicketDTOListIsReturned() {
        List<TicketDto> expectedTicketDTOs = DataDTOUtil.getTestTicketDTOs();

        List<Ticket> expectedTickets = DataUtil.getTestTickets();

        Mockito.when(ticketRepository.findAll()).thenReturn(expectedTickets);

        List<TicketDto> actualTicketDTOs = ticketService.findAll();

        assertEquals(expectedTicketDTOs.size(), actualTicketDTOs.size());

        for (int i = 0; i < expectedTicketDTOs.size(); i++) {
            TicketDto expectedTicketDTO = expectedTicketDTOs.get(i);
            TicketDto actualTicketDTO = actualTicketDTOs.get(i);

            assertEquals(expectedTicketDTO.getId(), actualTicketDTO.getId());
            assertEquals(expectedTicketDTO.getName(), actualTicketDTO.getName());
            assertEquals(expectedTicketDTO.getAmount(), actualTicketDTO.getAmount());
            assertEquals(expectedTicketDTO.getEventId(), actualTicketDTO.getEventId());
        }
    }

    @Test
    @Order(5)
     void checkUpdate_whenValidIdAndTicketDTO_thenReturnTicketDTO() {
        String newName = "NewTicketName";

        TicketDto changingTicketDTO = DataDTOUtil.getTestTicketDTO();
        changingTicketDTO.setName(newName);

        Ticket unchangedTicket = DataUtil.getTestTicket();

        Ticket changedTicket = DataUtil.getTestTicket();
        changedTicket.setName(newName);

        TicketDto expectedTicketDTO = DataDTOUtil.getTestTicketDTO();
        expectedTicketDTO.setName(newName);

        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findByIdForUpdate(1)).thenReturn(unchangedTicket);
        Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(changedTicket);

        TicketDto actual = ticketService.update(changingTicketDTO, 1);

        assertEquals(expectedTicketDTO.getName(), actual.getName());
        assertEquals(expectedTicketDTO.getAmount(), actual.getAmount());
    }

    @Test
    @Order(5)
     void checkUpdate_whenInvalidAmount_thenDataIntegrityViolation() {
        TicketDto changingTicketDTO = DataDTOUtil.getTestTicketDTO();
        changingTicketDTO.setAmount(-10);

        Ticket unchangedTicket = DataUtil.getTestTicket();

        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findByIdForUpdate(1)).thenReturn(unchangedTicket);

        assertThrows(DataIntegrityViolationException.class, () -> ticketService.update(changingTicketDTO, 1));
    }

    @Test
    @Order(5)
     void checkUpdate_whenInvalidEventId_thenConstraintViolation() {
        TicketDto failingTicketDTO = DataDTOUtil.getTestTicketDTO();
        failingTicketDTO.setEventId(0);

        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findByIdForUpdate(failingTicketDTO.getId())).thenReturn(ticketMapper.toEntity(failingTicketDTO));
        Mockito.when(ticketRepository.save(argThat(argument -> argument.getEvent().getId() == 0))).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> ticketService.update(failingTicketDTO, 1));
    }

    @Test
    @Order(5)
     void checkUpdate_whenTicketNotExists_throwEntityNotFoundException() {
        Integer notExistingId = 0;
        TicketDto changingTicket = DataDTOUtil.getTestTicketDTO();
        changingTicket.setId(notExistingId);

        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findByIdForUpdate(notExistingId)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> ticketService.update(changingTicket, notExistingId));
    }

    @Test
    @Order(7)
     void checkBuy_whenValidTicketDTOList_thenReturnTicketDTOList() {
        List<TicketDto> changingTicketDTOs = DataDTOUtil.getTestTicketDTOs();

        List<TicketDto> expectedTicketDTOs = DataDTOUtil.getTestTicketDTOs();
        expectedTicketDTOs.forEach(TicketDto -> TicketDto.setAmount(0));

        List<Ticket> unchangedTickets = DataUtil.getTestTickets();

        List<Ticket> changedTickets = DataUtil.getTestTickets();
        changedTickets.forEach(ticket -> {
            ticket.setAmount(0);
        });

        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findAllByIdForUpdate(any())).thenReturn(unchangedTickets);
        Mockito.when(ticketRepository.saveAll(any())).thenReturn(changedTickets);

        List<TicketDto> actualTicketDTOs = ticketService.buy(changingTicketDTOs);

        assertEquals(expectedTicketDTOs.size(), actualTicketDTOs.size());

        for (int i = 0; i < actualTicketDTOs.size(); i++) {
            TicketDto expected = expectedTicketDTOs.get(i);
            TicketDto actual = actualTicketDTOs.get(i);
            assertEquals(expected.getAmount(), actual.getAmount());
        }
    }

    @Test
    @Order(7)
     void checkBuy_whenBuyingTooMuch_thenThrowDataIntegrityViolation() {
        List<TicketDto> changingTicketDTOs = DataDTOUtil.getTestTicketDTOs();
        changingTicketDTOs.forEach(TicketDto -> {
            TicketDto.setAmount(100);
        });

        Mockito.when(ticketRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        List<Ticket> unchangedTickets = DataUtil.getTestTickets();
        Mockito.when(ticketRepository.findAllByIdForUpdate(any())).thenReturn(unchangedTickets);

        assertThrows(DataIntegrityViolationException.class, () -> ticketService.buy(changingTicketDTOs));
    }

    @Test
    @Order(7)
     void checkBuy_whenNotExistingTickets_thenThrowEntityNotFoundException() {
        List<TicketDto> changingTickets = DataDTOUtil.getTestTicketDTOs();
        List<Ticket> unchangedTickets = DataUtil.getTestTickets();
        unchangedTickets.remove(2);

        Mockito.when(ticketRepository.findAllByIdForUpdate(any())).thenReturn(unchangedTickets);

        assertThrows(EntityNotFoundException.class, () -> ticketService.buy(changingTickets));
    }

    @Test
    @Order(6)
     void checkDeleteById_whenInvalidId_thenEmptyResultDataAccess() {
        Mockito.doThrow(EmptyResultDataAccessException.class).when(ticketRepository).deleteById(eq(0));

        assertThrows(EmptyResultDataAccessException.class, () -> ticketService.deleteById(0));
    }

    @Test
    @Order(2)
     void checkFindAllUnsold_whenTicketsExist_thenReturnTicketDTOList() {
        List<TicketDto> expectedTicketDTOs = DataDTOUtil.getTestTicketDTOs();

        List<Ticket> expectedTickets = DataUtil.getTestTickets();

        Mockito.when(ticketRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findAllUnsold()).thenReturn(expectedTickets);

        List<TicketDto> actualTicketDTOs = ticketService.findAllUnsold();

        assertEquals(expectedTicketDTOs.size(), actualTicketDTOs.size());

        for (int i = 0; i < actualTicketDTOs.size(); i++) {
            TicketDto expected = expectedTicketDTOs.get(i);
            TicketDto actual = actualTicketDTOs.get(i);

            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getAmount(), actual.getAmount());
            assertEquals(expected.getEventId(), actual.getEventId());
        }
    }
}
