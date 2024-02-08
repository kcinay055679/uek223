package ch.bbt.uek223.ticketshop.models;

import ch.bbt.uek223.ticketshop.DataUtil;
import ch.bbt.uek223.ticketshop.event.Event;
import ch.bbt.uek223.ticketshop.person.Person;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Date;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(11)
class EventTest {

    @Test
    @Order(1)
    void constructor_hasEmptyConstructor() {
        Class<?> eventClass = Event.class;
        boolean doesEventClassHaveEmptyConstructor = Arrays.stream(eventClass.getConstructors()).anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesEventClassHaveEmptyConstructor);
    }

    @Test
    @Order(11)
    void class_isAnnotatedWithEntity() {
        assertNotNull(Event.class.getDeclaredAnnotation(Entity.class));
    }

    @Test
    @Order(2)
    void idField_doesExist() {
        assertDoesNotThrow(() -> Event.class.getDeclaredField("id"));
    }

    @Test
    @Order(12)
    void idField_isAnnotatedWithId() {
        try {
            assertNotNull(Event.class.getDeclaredField("id").getDeclaredAnnotation(Id.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(13)
    void idField_isAnnotatedWithGeneratedValue() {
        try {
            assertNotNull(Event.class.getDeclaredField("id").getDeclaredAnnotation(GeneratedValue.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(13)
    void idFieldGeneratedValueAnnotation_isStrategyIdentity() {
        try {
            assertEquals(GenerationType.IDENTITY, Event.class.getDeclaredField("id").getDeclaredAnnotation(GeneratedValue.class).strategy());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(3)
    void nameField_doesExist() {
        assertDoesNotThrow(() -> Event.class.getDeclaredField("name"));
    }

    @Test
    @Order(4)
    void ownerField_doesExist() {
        assertDoesNotThrow(() -> Event.class.getDeclaredField("owner"));
    }

    @Test
    @Order(14)
    void ownerFieldCardinality_isManyToOne() {
        try {
            assertNotNull(Event.class.getDeclaredField("owner").getDeclaredAnnotation(ManyToOne.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(15)
    void ownerField_isAnnotatedWithJoinColumn() {
        try {
            assertNotNull(Event.class.getDeclaredField("owner").getDeclaredAnnotation(JoinColumn.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(15)
    void ownerFieldJoinColumnAnnotation_isNameOwnerId() {
        try {
            assertEquals("owner_id", Event.class.getDeclaredField("owner").getDeclaredAnnotation(JoinColumn.class).name());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(5)
    void ticketsField_doesExist() {
        assertDoesNotThrow(() -> Event.class.getDeclaredField("tickets"));
    }

    @Test
    @Order(16)
    void ticketsFieldCardinality_isOneToMany() {
        try {
            assertNotNull(Event.class.getDeclaredField("tickets").getDeclaredAnnotation(OneToMany.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(16)
    void ticketsFieldOneToManyAnnotation_isMappedByEvent() {
        try {
            assertEquals("event", Event.class.getDeclaredField("tickets").getDeclaredAnnotation(OneToMany.class).mappedBy());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(6)
    void dateField_doesExist() {
        assertDoesNotThrow(() -> Event.class.getDeclaredField("date"));
    }

    @Test
    @Order(7)
    void descriptionField_doesExist() {
        assertDoesNotThrow(() -> Event.class.getDeclaredField("description"));
    }

    @Test
    @Order(8)
    void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> Event.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(8)
    void equalsMethod_comparesOnlyId() {
        Event event1 = DataUtil.getTestEvent();
        Event event2 = DataUtil.getTestEvent();

        event1.setName("NotSameName");
        event1.setDescription("NotSameDescription");
        event1.setDate(Date.valueOf("1970-01-01"));

        assertEquals(event1, event2);
    }

    @Test
    @Order(9)
    void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> Event.class.getDeclaredMethod("hashCode"));
    }

    @Test
    @Order(9)
    void hashCodeMethod_hashesId() {
        Event event1 = DataUtil.getTestEvent();
        Event event2 = DataUtil.getTestEvent();

        event1.setDescription("Different description");
        event1.setName("Not same name");
        event1.setOwner(new Person());

        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getName", "getOwner", "getDate", "getDescription", "getTickets"})
    @Order(10)
    void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> Event.class.getDeclaredMethod(getterName));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setName, java.lang.String", "setOwner,ch.bbt.uek223.ticketshop.person.Person", "setDate, java.sql.Date", "setDescription, java.lang.String", "setTickets, java.util.List"})
    @Order(10)
    void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> Event.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }
}
