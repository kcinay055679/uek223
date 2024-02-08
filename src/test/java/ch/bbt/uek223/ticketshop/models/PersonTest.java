package ch.bbt.uek223.ticketshop.models;


import ch.bbt.uek223.ticketshop.DataUtil;
import ch.bbt.uek223.ticketshop.person.Person;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(10)
class PersonTest {

    @Test
    @Order(1)
    void constructor_hasEmptyConstructor() {
        Class<?> personClass = Person.class;
        boolean doesPersonClassHaveEmptyConstructor = Arrays.stream(personClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesPersonClassHaveEmptyConstructor);
    }

    @Test
    @Order(9)
    void class_isAnnotatedWithEntity() {
        assertNotNull(Person.class.getDeclaredAnnotation(Entity.class));
    }

    @Test
    @Order(2)
    void idField_doesExist() {
        assertDoesNotThrow(() -> Person.class.getDeclaredField("id"));
    }

    @Test
    @Order(10)
    void idField_isAnnotatedWithId() {
        try {
            assertNotNull(Person.class
                    .getDeclaredField("id")
                    .getDeclaredAnnotation(Id.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(11)
    void idField_isAnnotatedWithGeneratedValue() {
        try {
            assertNotNull(Person.class
                    .getDeclaredField("id")
                    .getDeclaredAnnotation(GeneratedValue.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(11)
    void idFieldGeneratedValueAnnotation_isStrategyIdentity() {
        try {
            assertEquals(GenerationType.IDENTITY, Person.class
                    .getDeclaredField("id")
                    .getDeclaredAnnotation(GeneratedValue.class)
                    .strategy()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(3)
    void emailField_doesExist() {
        assertDoesNotThrow(() -> Person.class.getDeclaredField("email"));
    }

    @Test
    @Order(12)
    void emailField_isAnnotatedWithColumn() {
        try {
            assertNotNull(Person.class
                    .getDeclaredField("email")
                    .getDeclaredAnnotation(Column.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(12)
    void emailFieldColumn_isUnique() {
        try {
            assertTrue(Person.class
                    .getDeclaredField("email")
                    .getDeclaredAnnotation(Column.class)
                    .unique());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(4)
    void passwordHashField_doesExist() {
        assertDoesNotThrow(() -> Person.class.getDeclaredField("password"));
    }

    @Test
    @Order(5)
    void eventsField_doesExist() {
        assertDoesNotThrow(() -> Person.class.getDeclaredField("events"));
    }

    @Test
    @Order(13)
    void eventsFieldCardinality_isOneToMany() {
        try {
            assertNotNull(Person.class
                    .getDeclaredField("events")
                    .getDeclaredAnnotation(OneToMany.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(13)
    void eventsFieldOneToManyAnnotation_isMappedByOwner() {
        try {
            assertEquals("owner", Person.class
                    .getDeclaredField("events")
                    .getDeclaredAnnotation(OneToMany.class)
                    .mappedBy()
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(6)
    void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> Person.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(6)
    void equalsMethod_comparesOnlyId() {
        Person person1 = DataUtil.getTestPerson();
        Person person2 = DataUtil.getTestPerson();

        person1.setEmail("NotSameEmail@foo.bar");
        person1.setPassword("NotSamePassword");

        assertEquals(person1, person2);
    }

    @Test
    @Order(7)
    void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> Person.class.getDeclaredMethod("hashCode"));
    }

    @Test
    @Order(7)
    void hashCodeMethod_hashesId() {
        Person person1 = DataUtil.getTestPerson();
        Person person2 = DataUtil.getTestPerson();

        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getEmail", "getPassword", "getEvents"})
    @Order(8)
    void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> Person.class.getDeclaredMethod(getterName));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setEmail, java.lang.String", "setPassword, java.lang.String", "setEvents, java.util.Set"})
    @Order(8)
    void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> Person.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

    // For roles implementation
    @Test
    @Order(14)
    void assignedRolesField_doesExist() {
        assertDoesNotThrow(() -> Person.class.getDeclaredField("assignedRoles"));
    }

    @Test
    @Order(15)
    @Disabled
    void assignedRolesField_isOneToMany() {
        try {
            assertNotNull(Person.class
                    .getDeclaredField("assignedRoles")
                    .getDeclaredAnnotation(ManyToMany.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(15)
    @Disabled
    void assignedRolesField_isAnnotatedWithJoinTable() {
        try {
            assertNotNull(Person.class
                    .getDeclaredField("assignedRoles")
                    .getDeclaredAnnotation(JoinTable.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }
}
