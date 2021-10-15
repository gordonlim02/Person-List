package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    public Person dummy;

    @BeforeEach
    void createDummy() {
        dummy = new Person("John Doe", "male", "brown", "CPSC 210");
    }

    @Test
    void testPerson() {
        assertEquals( "John Doe", dummy.getName());
        assertEquals( "male", dummy.getGender());
        assertEquals( "brown", dummy.getHairColor());
        assertEquals( "CPSC 210", dummy.getWhereMet());
    }

    @Test
    void testModifyPerson() {
        dummy.modifyPerson("Jane Doe", "female", "black", "CPSC 121");
        assertEquals( "Jane Doe", dummy.getName());
        assertEquals( "female", dummy.getGender());
        assertEquals( "black", dummy.getHairColor());
        assertEquals( "CPSC 121", dummy.getWhereMet());
    }

    @Test
    void testDeletePerson() {
        dummy.deletePerson();
        assertNull(dummy.getName());
        assertNull(dummy.getGender());
        assertNull(dummy.getHairColor());
        assertNull(dummy.getWhereMet());
    }

    @Test
    void testIsNull() {
        assertFalse(dummy.isNull());
        dummy.deletePerson();
        assertTrue(dummy.isNull());
    }

    @Test
    void testPersonString() {
        assertEquals("Name: John Doe | Gender: male | Hair color: brown" +
                " | Place of encounter: CPSC 210", dummy.personString());
        dummy.deletePerson();
        assertEquals("", dummy.personString());
    }
}