package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    public PersonList sampleList;
    public Person dummy;

    @BeforeEach
    void init() {
        sampleList = new PersonList();
        dummy = new Person("John Doe", "male", "brown", "CPSC 210");
    }

    @Test
    void testPersonConstructor() {
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
    void testSamePerson() {
        Person dummy2 = new Person("John Doe", "male", "brown", "CPSC 210");
        Person dummy3 = new Person("John Do", "male", "brown", "CPSC 210");
        assertTrue(dummy.samePersonAs(dummy2));
        assertFalse(dummy.samePersonAs(dummy3));
    }

    @Test
    void testPersonString() {
        assertEquals("Name: John Doe | Gender: male | Hair color: brown" +
                " | Place of encounter: CPSC 210", dummy.personString());
    }

    @Test
    void testToJson() {
        JSONObject dummyJson = dummy.toJson();
        assertEquals(dummyJson.getString("name"), "John Doe");
        assertEquals(dummyJson.getString("gender"), "male");
        assertEquals(dummyJson.getString("hair color"), "brown");
        assertEquals(dummyJson.getString("place of encounter"), "CPSC 210");
    }
}