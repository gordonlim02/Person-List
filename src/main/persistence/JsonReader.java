package persistence;

import model.Person;
import model.PersonList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// This class is copied and modified from the JsonSerializationDemo project
// A reader that reads the personList from the JSON data in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads personList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PersonList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePersonList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses personList from JSON object and returns it
    private PersonList parsePersonList(JSONObject jsonObject) {
        PersonList personList = new PersonList();
        addPersons(personList, jsonObject);
        return personList;
    }

    // MODIFIES: personList
    // EFFECTS: parses thingies from JSON object and adds them to personList
    private void addPersons(PersonList personList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("personList");
        for (Object json : jsonArray) {
            JSONObject nextPerson = (JSONObject) json;
            addPerson(personList, nextPerson);
        }
    }

    // MODIFIES: personList
    // EFFECTS: parses person from JSON object and adds it to personList
    private void addPerson(PersonList personList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String gender = jsonObject.getString("gender");
        String hairColor = jsonObject.getString("hair color");
        String whereMet = jsonObject.getString("place of encounter");
        Person person = new Person(name, gender, hairColor, whereMet);
        personList.addPerson(person);
    }
}
