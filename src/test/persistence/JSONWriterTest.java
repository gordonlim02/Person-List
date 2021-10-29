package persistence;

import model.Person;
import model.PersonList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This class is copied and modified from the JsonSerializationDemo project
public class JSONWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            PersonList personList = new PersonList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPersonList() {
        try {
            PersonList personList = new PersonList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPersonList.json");
            writer.open();
            writer.write(personList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPersonList.json");
            personList = reader.read();
            assertEquals(0, personList.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPersonList() {
        try {
            PersonList personList = new PersonList();
            personList.addPerson(new Person("John Doe", "male", "brown", "CPSC 210"));
            personList.addPerson(new Person("Jane Doe", "female", "black", "CPSC 121"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPersonList.json");
            writer.open();
            writer.write(personList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPersonList.json");
            personList = reader.read();
            ArrayList<Person> persons = personList.getPersonList();
            assertEquals(2, persons.size());
            assertEquals("John Doe", persons.get(0).getName());
            assertEquals("CPSC 121", persons.get(1).getWhereMet());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
