package model;

// Represents a person with a name, gender, hair color, and where the user has met the person.
public class Person {
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

    // REQUIRES: A non-null Person object
    // MODIFIES: this
    // EFFECTS: set the path to the Person object to null
    //          (the object will then be automatically removed by the garbage collector)
    public void deletePerson() {
        name = null;
        gender = null;
        hairColor = null;
        whereMet = null;
    }

    // EFFECTS: returns true if the fields of the Person object are all null
    public boolean isNull() {
        return (name == null && gender == null && hairColor ==  null && whereMet == null);
    }

    //EFFECTS: returns a string that contains the information of the Person object
    public String personString() {
        if (this.isNull()) {
            return "";
        }
        return ("Name: " + name + " | Gender: " + gender + " | Hair color: "
                + hairColor + " | Place of encounter: " + whereMet);
    }
}