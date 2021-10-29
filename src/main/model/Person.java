package model;

import org.json.JSONObject;
import persistence.Writable;

import java.lang.reflect.Field;

// Represents a person with a name, gender, hair color, and where the user has met the person.
public class Person implements Writable {
    private String name;        // name of the person
    private String gender;      // gender of the person
    private String hairColor;   // hair color of the person
    private String whereMet;    // where the user has met the person

    // EFFECTS: create a Person object and set the name, gender, hair color,
    //            and the place of encounter to the entered values
    public Person(String name, String gender, String hairColor, String whereMet) {
        this.name = name;
        this.gender = gender;
        this.hairColor = hairColor;
        this.whereMet = whereMet;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getHairColor() {
        return hairColor;
    }

    public String getWhereMet() {
        return whereMet;
    }

    // MODIFIES: this
    // EFFECTS: new name, gender, hairColor, and place of encounter, are assigned to this Person object
    public void modifyPerson(String name, String gender, String hairColor, String whereMet) {
        this.name = name;
        this.gender = gender;
        this.hairColor = hairColor;
        this.whereMet = whereMet;
    }

    // EFFECTS: returns true if this person has the same fields as person p
    public boolean samePersonAs(Person newPerson) {
        boolean sameName = this.getName().equals(newPerson.getName());
        boolean sameGender = this.getGender().equals(newPerson.getGender());
        boolean sameHairColor = this.getHairColor().equals(newPerson.getHairColor());
        boolean sameWhereMet = this.getWhereMet().equals(newPerson.getWhereMet());
        return (sameName && sameGender && sameHairColor && sameWhereMet);
    }

    // EFFECTS: returns a string that contains the information of the Person object
    public String personString() {
        return ("Name: " + name + " | Gender: " + gender + " | Hair color: "
                + hairColor + " | Place of encounter: " + whereMet);
    }

    // Copied and modified from the JsonSerializationDemo project
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("gender", gender);
        json.put("hair color", hairColor);
        json.put("place of encounter", whereMet);
        return json;
    }
}