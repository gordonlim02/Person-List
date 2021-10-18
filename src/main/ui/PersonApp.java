package ui;

import model.Person;
import model.PersonList;

import java.util.Scanner;

// Text-based UI of the NamesDB Application
public class PersonApp {
    private Scanner scanner;
    private Person person;
    private PersonList personList;

    // EFFECTS: runs the UI of the application
    public PersonApp() {
        scanner = new Scanner(System.in);
        personList = new PersonList();
        while (true) {
            printPersonList();
            mainInputPrompt();
            String input = scanner.nextLine();
            if (input.equals("4")) {
                break;
            }
            chooseAction(input);
        }
    }

    // EFFECTS: prints the main input prompt
    private void mainInputPrompt() {
        System.out.println();
        System.out.println("Please choose an operation: (Enter a number from 1 to 4)");
        System.out.println("1. Add a person");
        System.out.println("2. Edit a person");
        System.out.println("3. Delete a person");
        System.out.println("4. Quit this application");
    }

    // REQUIRES: a string that is not "4"
    // EFFECTS: determines the action to carry out
    public void chooseAction(String input) {
        if (input.equals("1")) {
            addPerson();
        } else if (input.equals("2")) {
            editPerson();
        } else if (input.equals("3")) {
            deletePerson();
        } else {
            System.out.println();
            System.out.println("That was not a valid input. Please try again.");
        }
    }

    // REQUIRES: there is only 1 Person with the specified name
    // EFFECTS: returns a Person with the specified name
    public Person choosePerson() {
        String name = printPromptTakeInput("Please enter the name of the person:");
        Person p = personList.choosePerson(name);
        if (p == null) {
            System.out.println("There is no person with that name in the database. Please try again.");
            return null;
        } else {
            return p;
        }
    }

    // MODIFIES: personList
    // EFFECTS: adds a new Person object to the database
    public void addPerson() {
        String name = printPromptTakeInput("Please enter the name of the person:");
        String gender = printPromptTakeInput("Please enter the gender of the person:");
        String hairColor = printPromptTakeInput("Please enter the hair color of the person:");
        String whereMet = printPromptTakeInput("Please enter where you have met the person:");
        person = new Person(name, gender, hairColor, whereMet);
        personList.addPerson(person);
        System.out.println();
        System.out.println("The person " + name + " has been added to the database.");
    }

    // EFFECTS: prints information about all persons
    public void printPersonList() {
        System.out.println();
        System.out.println("List of all persons");
        System.out.println("--------------------");
        if (personList.isEmpty()) {
            System.out.println("There is currently 0 person in the database.");
        }
        for (String s : personList.personListString()) {
            System.out.println(s);
        }
    }

    // MODIFIES: person, personList
    // EFFECTS: edits the information about the person
    public void editPerson() {
        Person person = choosePerson();
        if (person == null) {
            return;
        }
        String name = printPromptTakeInput("Please enter the new name of the person:");
        String gender = printPromptTakeInput("Please enter the new gender of the person:");
        String hairColor = printPromptTakeInput("Please enter the new hair color of the person:");
        String whereMet = printPromptTakeInput("Please enter where you have met the person:");
        person.modifyPerson(name, gender, hairColor, whereMet);
    }

    public void deletePerson() {
        Person person = choosePerson();
        if (person == null) {
            return;
        }
        personList.deletePerson(person);

    }

    // EFFECTS: prints the prompt and returns the next line scanned by the scanner
    public String printPromptTakeInput(String prompt) {
        System.out.println();
        System.out.println(prompt);
        return scanner.nextLine();
    }
}
