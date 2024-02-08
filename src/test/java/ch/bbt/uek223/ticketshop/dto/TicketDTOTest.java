package ch.bbt.uek223.ticketshop.dto;

import ch.bbt.uek223.ticketshop.ticket.dto.TicketDto;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(26)
 class TicketDTOTest {
    @Test
    @Order(1)
     void constructor_hasEmptyConstructor() {
        Class<?> TicketDtoClass = TicketDto.class;
        boolean doesEventClassHaveEmptyConstructor = Arrays.stream(TicketDtoClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesEventClassHaveEmptyConstructor);
    }

    @Test
    @Order(2)
     void idField_doesExist() {
        assertDoesNotThrow(() -> TicketDto.class.getDeclaredField("id"));
    }

    @Test
    @Order(3)
     void nameField_doesExist() {
        assertDoesNotThrow(() -> TicketDto.class.getDeclaredField("name"));
    }

    @Test
    @Order(4)
     void descriptionField_doesExist() {
        assertDoesNotThrow(() -> TicketDto.class.getDeclaredField("description"));
    }

    @Test
    @Order(5)
     void amountToBuyField_doesExist() {
        assertDoesNotThrow(() -> TicketDto.class.getDeclaredField("amount"));
    }

    @Test
    @Order(6)
     void eventIdField_doesExist() {
        assertDoesNotThrow(() -> TicketDto.class.getDeclaredField("eventId"));
    }

    @Test
    @Order(10)
     void eventIdField_isAnnotatedWithNotNull() {
        try {
            assertNotNull(TicketDto.class
                    .getDeclaredField("eventId")
                    .getDeclaredAnnotation(NotNull.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(7)
     void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> TicketDto.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(8)
     void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> TicketDto.class.getDeclaredMethod("hashCode"));
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getName", "getDescription", "getAmount", "getEventId"})
    @Order(9)
     void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> TicketDto.class.getDeclaredMethod(getterName));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setName, java.lang.String", "setDescription, java.lang.String", "setAmount, java.lang.Integer", "setEventId, java.lang.Integer"})
    @Order(9)
     void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> TicketDto.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }
}
