package ch.bbt.uek223.ticketshop.dto;

import ch.bbt.uek223.ticketshop.event.dto.EventDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(25)
class EventDTOTest {

    @Test
    @Order(1)
    void constructor_hasEmptyConstructor() {
        Class<?> EventDtoClass = EventDto.class;
        boolean doesEventDtoClassHaveEmptyConstructor = Arrays.stream(EventDtoClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
        assertTrue(doesEventDtoClassHaveEmptyConstructor);
    }

    @Test
    @Order(2)
    void idField_doesExist() {
        assertDoesNotThrow(() -> EventDto.class.getDeclaredField("id"));
    }

    @Test
    @Order(3)
    void nameField_doesExist() {
        assertDoesNotThrow(() -> EventDto.class.getDeclaredField("name"));
    }

    @Test
    @Order(11)
    void nameField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(EventDto.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(4)
    void descriptionField_doesExist() {
        assertDoesNotThrow(() -> EventDto.class.getDeclaredField("description"));
    }

    @Test
    @Order(5)
    void ownerIdField_doesExist() {
        assertDoesNotThrow(() -> EventDto.class.getDeclaredField("ownerId"));
    }

    @Test
    @Order(12)
    void ownerIdField_isAnnotatedWithNotNull() {
        try {
            assertNotNull(EventDto.class
                    .getDeclaredField("ownerId")
                    .getDeclaredAnnotation(NotNull.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(6)
    void dateField_doesExist() {
        assertDoesNotThrow(() -> EventDto.class.getDeclaredField("date"));
    }

    @Test
    @Order(13)
    void dateField_isAnnotatedWithNotNull() {
        try {
            assertNotNull(EventDto.class
                    .getDeclaredField("date")
                    .getDeclaredAnnotation(NotNull.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(7)
    void ticketIdsField_doesExist() {
        assertDoesNotThrow(() -> EventDto.class.getDeclaredField("ticketIds"));
    }

    @Test
    @Order(8)
    void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> EventDto.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(9)
    void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> EventDto.class.getDeclaredMethod("hashCode"));
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getName", "getOwnerId", "getTicketIds", "getDate", "getDescription"})
    @Order(10)
    void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> EventDto.class.getDeclaredMethod(getterName));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setName, java.lang.String", "setOwnerId, java.lang.Integer", "setTicketIds, java.util.List", "setDate, java.sql.Date", "setDescription, java.lang.String"})
    @Order(10)
    void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> EventDto.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }
}
