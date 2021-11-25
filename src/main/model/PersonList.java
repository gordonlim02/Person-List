package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;

// A personList class that contains an arrayList of persons
public class PersonList extends ArrayList<Person> implements Writable {
    private ArrayList<Person> personList;
    private Iterator<Person> iterator;

    // EFFECTS: initializes a personList as an empty list
    public PersonList() {
        personList = new ArrayList<>();
    }

    public ArrayList<Person> getPersonList() {
        return personList;
    }

    // MODIFIES: this
    // EFFECTS: adds a person and returns true; returns false if the
    //          person is already in the list
    public boolean addPerson(Person newPerson) {
        for (Person existingPerson : personList) {
            if (newPerson.samePersonAs(existingPerson)) {
                return false;
            }
        }
        personList.add(newPerson);
        EventLog.getInstance().logEvent(new Event("Added a person named " + newPerson.getName()));
        return true;
    }

    // REQUIRES: there is a person in the list with the name
    // EFFECTS: returns a Person with the given name
    public Person choosePerson(String name) {
        for (Person p : personList) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    // MODIFIES: personList
    // EFFECTS: delete a Person from the PersonList
    public void deletePerson(Person person) {
        iterator = personList.iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (person == p) {
                iterator.remove();
            }
        }
        EventLog.getInstance().logEvent(new Event("Deleted a person named " + person.getName()));
    }

    // EFFECTS: returns the information of the persons in PersonList as a String ArrayList
    public ArrayList<String> personListString() {
        ArrayList<String> resultString = new ArrayList<>();
        for (Person p : personList) {
            resultString.add(p.personString());
        }
        return resultString;
    }

    // MODIFIES: personList
    // EFFECTS: change the oldPerson in the personList to newPerson
    public void changePerson(Person oldPerson, Person newPerson) {
        for (Person person : personList) {
            if (person == oldPerson) {
                personList.set(personList.indexOf(oldPerson), newPerson);
            }
        }
        EventLog.getInstance().logEvent(new Event("Edited a person originally named " + oldPerson.getName()));
    }

    // EFFECTS: returns true if personList is empty, false otherwise
    public boolean isEmpty() {
        return personList.isEmpty();
    }

    // Copied and modified from the JsonSerializationDemo project
    // EFFECTS: returns a JSONObject that contains a list of persons
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("personList", personsToList());
        return json;
    }

    // Copied and modified from the JsonSerializationDemo project
    // EFFECTS: returns things in this personList as a JSON array
    public JSONArray personsToList() {
        JSONArray jsonArray = new JSONArray();
        for (Person p : personList) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }
}
