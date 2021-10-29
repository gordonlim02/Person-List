package persistence;

import model.Person;
import model.PersonList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This class is copied and modified from the JsonSerializationDemo project
public class JSONReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PersonList personList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPersonList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPersonList.json");
        try {
            PersonList personList = reader.read();
            assertEquals(0, personList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPersonList.json");
        try {
            ArrayList<Person> personList = reader.read().getPersonList();
            assertEquals(2, personList.size());
            assertEquals("John Doe", personList.get(0).getName());
            assertEquals("CPSC 121", personList.get(1).getWhereMet());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
