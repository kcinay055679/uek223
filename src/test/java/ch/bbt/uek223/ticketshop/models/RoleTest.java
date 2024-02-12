package ch.bbt.uek223.ticketshop.models;

import ch.bbt.uek223.ticketshop.DataUtil;
import ch.bbt.uek223.ticketshop.role.Role;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    public void constructor_hasEmptyConstructor() {
        Class<?> roleClass = Role.class;
        boolean doesRoleClassHaveEmptyConstructor = Arrays.stream(roleClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesRoleClassHaveEmptyConstructor);
    }

    @Test
    public void class_isAnnotatedWithEntity() {
        assertNotNull(Role.class.getDeclaredAnnotation(Entity.class));
    }

    @Test
    public void ifField_doesExist() {
        assertDoesNotThrow(() -> Role.class.getDeclaredField("id"));
    }

    @Test
    public void idField_isAnnotatedWithId() {
        try {
            assertNotNull(Role.class
                    .getDeclaredField("id")
                    .getDeclaredAnnotation(Id.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void idField_isAnnotatedWithGeneratedValue() {
        try {
            assertNotNull(Role.class
                    .getDeclaredField("id")
                    .getDeclaredAnnotation(GeneratedValue.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void idFieldGeneratedValue_isStrategyIdentity() {
        try {
            assertEquals(GenerationType.IDENTITY, Role.class
                    .getDeclaredField("id")
                    .getDeclaredAnnotation(GeneratedValue.class)
                    .strategy()
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void nameField_doesExist() {
        assertDoesNotThrow(() -> Role.class.getDeclaredField("name"));
    }

    @Test
    public void nameField_isAnnotatedWithColumn() {
        try {
            assertNotNull(Role.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(Column.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void nameFieldColumn_isNotNullable() {
        try {
            assertFalse(Role.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(Column.class)
                    .nullable());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void nameFieldColumn_isUnique() {
        try {
            assertTrue(Role.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(Column.class)
                    .unique());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void assignedPersonsField_doesExist() {
        assertDoesNotThrow(() -> Role.class.getDeclaredField("persons"));
    }

    @Test
    public void assignedPersonsFieldCardinality_isManyToMany() {
        try {
            assertNotNull(Role.class
                    .getDeclaredField("persons")
                    .getDeclaredAnnotation(ManyToMany.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> Role.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    public void equalsMethod_comparesOnlyId() {
        Role role1 = DataUtil.getTestRole();
        Role role2 = DataUtil.getTestRole();

        role1.setName("Not same name");

        assertEquals(role1, role2);
    }

    @Test
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> Role.class.getDeclaredMethod("hashCode"));
    }

    @Test
    public void hashCodeMethod_hashesOnlyId() {
        Role role1 = DataUtil.getTestRole();
        Role role2 = DataUtil.getTestRole();

        role1.setName("Not same name");

        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getName", "getPersons"})
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> Role.class.getDeclaredMethod(getterName));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setName, java.lang.String", "setPersons, java.util.Set"})
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> Role.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }
}