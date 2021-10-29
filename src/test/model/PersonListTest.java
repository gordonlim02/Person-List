package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PersonListTest {
    public PersonList personList;
    public Person dummy;
    public Person dummy2;

    @BeforeEach
    void init() {
        personList = new PersonList();
        dummy = new Person("John Doe", "male", "brown", "CPSC 210");
        dummy2 = new Person("Jane Doe", "female", "black", "CPSC 121");
    }

    @Test
    void testGetPersonList() {
        ArrayList<Person> expectedPersonList = new ArrayList<>();
        expectedPersonList.add(dummy);
        ArrayList<Person> testPersonList = personList.getPersonList();
        testPersonList.add(dummy);
        assertEquals(expectedPersonList.get(0), expectedPersonList.get(0));
    }

    @Test
    void testAddPerson() {
        assertTrue(personList.addPerson(dummy));
        assertFalse(personList.addPerson(dummy));
    }

    @Test
    void testChoosePerson() {
        personList.addPerson(dummy);
        assertEquals(dummy, personList.choosePerson("John Doe"));
        assertNull(personList.choosePerson("abc"));
    }

    @Test
    void testDeletePerson() {
        personList.addPerson(dummy);
        personList.deletePerson(dummy);
        assertTrue(personList.isEmpty());
    }

    @Test
    void testPersonListString() {
        assertTrue(personList.personListString().isEmpty());
        personList.addPerson(dummy);
        ArrayList<String> testList = personList.personListString();
        String testString = testList.get(0);
        assertEquals(testString, dummy.personString());
    }

    @Test
    void testJson() {
        personList.addPerson(dummy);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(dummy.toJson());
        jsonObject.put("personList", jsonArray);
        personList.toJson().similar(jsonObject);
    }
}
