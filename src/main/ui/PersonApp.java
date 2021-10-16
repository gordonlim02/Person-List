package ui;

import model.Person;

import java.util.Scanner;

// Text-based UI of the NamesDB Application
public class PersonApp {
    private Scanner scanner;
    private Person person;

    // MODIFIES: person
    // EFFECTS: runs the UI of the application
    public PersonApp() {
        while (true) {
            scanner = new Scanner(System.in);
            inputPrompt();
            String input = scanner.nextLine();
            if (input.equals("5")) {
                break;
            }
            chooseAction(input);
        }
    }

    // EFFECTS: prints the input prompt
    private void inputPrompt() {
        System.out.println("Please choose an action");
        System.out.println("1. Add a person");
        System.out.println("2. View information about the person");
        System.out.println("3. Edit information about the person");
        System.out.println("4. Delete the person");
        System.out.println("5. Quit this application");
    }

    // REQUIRES: a string that is not "5"
    // EFFECTS: determines the action to carry out
    public void chooseAction(String input) {
        if (input.equals("1")) {
            createPerson();
        } else if (input.equals("2")) {
            viewPerson();
        } else if (input.equals("3")) {
            editPerson();
        } else if (input.equals("4")) {
            deletePerson();
        } else {
            System.out.println();
            System.out.println("That is not a valid input. Please try again.");
            System.out.println();
        }
    }

    // EFFECTS: creates a new Person object
    public void createPerson() {
        System.out.println("Please enter the name of the person");
        String name = scanner.nextLine();
        System.out.println("Please enter the gender of the person");
        String gender = scanner.nextLine();
        System.out.println("Please enter the hair color of the person");
        String hairColor = scanner.nextLine();
        System.out.println("Please enter where you have met the person");
        String whereMet = scanner.nextLine();
        person = new Person(name, gender, hairColor, whereMet);
    }

    // EFFECTS: prints information about the person
    public void viewPerson() {
        System.out.println(person.personString());
    }

    // MODIFIES: person
    // EFFECTS: edits the information about the person
    public void editPerson() {
        System.out.println("Please enter the name of the person");
        String name = scanner.nextLine();
        System.out.println("Please enter the gender of the person");
        String gender = scanner.nextLine();
        System.out.println("Please enter the hair color of the person");
        String hairColor = scanner.nextLine();
        System.out.println("Please enter where you have met the person");
        String whereMet = scanner.nextLine();
        person.modifyPerson(name, gender, hairColor, whereMet);
    }

    // MODIFIES: person
    // EFFECTS: delete the person's information
    private void deletePerson() {
        person.deletePerson();
    }
}
