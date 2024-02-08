package ch.bbt.uek223.ticketshop.dto;

import ch.bbt.uek223.ticketshop.person.dto.PersonResponseDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(23)
 class PersonResponseDTOTest {
    @Test
    @Order(1)
     void constructor_hasEmptyConstructor() {
        Class<?> PersonResponseDtoClass = PersonResponseDto.class;
        boolean doesPersonResponseDtoClassHaveEmptyConstructor = Arrays.stream(PersonResponseDtoClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesPersonResponseDtoClassHaveEmptyConstructor);
    }

    @Test
    @Order(2)
     void idField_doesExist() {
        assertDoesNotThrow(() -> PersonResponseDto.class.getDeclaredField("id"));
    }

    @Test
    @Order(3)
     void emailField_doesExist() {
        assertDoesNotThrow(() -> PersonResponseDto.class.getDeclaredField("email"));
    }

    @Test
    @Order(4)
     void eventIdsField_doesExist() {
        assertDoesNotThrow(() -> PersonResponseDto.class.getDeclaredField("eventIds"));
    }

    @Test
    @Order(5)
     void passwordField_doesNotExist() {
        assertThrows(NoSuchFieldException.class, () -> PersonResponseDto.class.getDeclaredField("password"));
    }

    @Test
    @Order(6)
     void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonResponseDto.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(7)
     void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonResponseDto.class.getDeclaredMethod("hashCode"));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setEmail, java.lang.String", "setEventIds, java.util.List"})
    @Order(8)
     void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> PersonResponseDto.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getEmail", "getEventIds"})
    @Order(8)
     void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> PersonResponseDto.class.getDeclaredMethod(getterName));
    }
}
