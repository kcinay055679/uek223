package ch.bbt.uek223.ticketshop.dto;

import ch.bbt.uek223.ticketshop.DataDTOUtil;
import ch.bbt.uek223.ticketshop.person.dto.PersonRequestDto;
import ch.bbt.uek223.ticketshop.person.dto.PersonResponseDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(24)
 class PersonRequestDTOTest {
    @Test
    @Order(1)
     void constructor_hasEmptyConstructor() {
        Class<?> personRequestDTOClass = PersonRequestDTOTest.class;
        boolean doesPersonRequestDTOCLassHaveEmptyConstructor = Arrays.stream(personRequestDTOClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
        assertTrue(doesPersonRequestDTOCLassHaveEmptyConstructor);
    }

    @Test
    @Order(2)
     void personRequestDTO_extendsPersonResponseDTO() {
        assertEquals(PersonRequestDto.class.getSuperclass(), PersonResponseDto.class);
    }

    @Test
    @Order(3)
     void passwordField_doesExist() {
        assertDoesNotThrow(() -> PersonRequestDto.class.getDeclaredField("password"));
    }

    @Test
    @Order(6)
     void getPasswordMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonRequestDto.class.getDeclaredMethod("getPassword"));
    }

    @Test
    @Order(6)
     void setPasswordMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonRequestDto.class.getDeclaredMethod("setPassword", String.class));
    }

    @Test
    @Order(4)
     void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonRequestDto.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(5)
     void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonRequestDto.class.getDeclaredMethod("hashCode"));
    }

    @Test
    @Order(4)
     void equalsMethod_usesSuperEquals() {
        PersonRequestDto p1 = DataDTOUtil.getTestPersonRequestDTO();
        PersonRequestDto p2 = DataDTOUtil.getTestPersonRequestDTO();

        p1.setEmail("NotSameEmail");

        assertNotEquals(p1, p2);
    }

    @Test
    @Order(5)
     void hashCodeMethod_usesSuperHashCode() {
        PersonRequestDto p1 = DataDTOUtil.getTestPersonRequestDTO();
        PersonRequestDto p2 = DataDTOUtil.getTestPersonRequestDTO();

        p1.setEmail("NotSameEmail");

        assertNotEquals(p1.hashCode(), p2.hashCode());
    }
}
