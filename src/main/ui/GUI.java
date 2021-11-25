package ui;

import model.LogPrinter;
import model.Person;
import model.PersonList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// The GUI of the namesDB application
public class GUI {
    private PersonList personList = new PersonList();
    private final DefaultTableModel tableModel;
    private final JTable table;

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public GUI() {
        // set up JFrame
        JFrame frame = new JFrame("NamesDB");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set up JPanel with gridBagLayout
        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        frame.add(panel);

        // set up JTable
        String[] columnNames = {"Name", "Gender", "Hair Color", "Place of Encounter"};
        String[][] data = {};
        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panel.add(scrollPane, gridBagConstraints);

        // add the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel.add(buttonPanel, gridBagConstraints);
        JButton loadButton = new JButton("Load");
        JButton saveButton = new JButton("Save");
        JButton editButton = new JButton("Edit");
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        // action for loadButton
        loadButton.addActionListener(e -> {
            JsonReader jsonReader = new JsonReader("./data/personList.json");
            try {
                personList = jsonReader.read();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            updateTableModel();
        });

        // action for saveButton
        saveButton.addActionListener(e -> {
            JsonWriter jsonWriter = new JsonWriter("./data/personList.json");
            try {
                jsonWriter.open();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            jsonWriter.write(personList);
            jsonWriter.close();
        });

        // action for addButton
        addButton.addActionListener(e -> {
            Person newPerson = createNewPersonFromInput();
            if (newPerson != null) {
                personList.addPerson(newPerson);
            }
            updateTableModel();
        });

        // action for editButton
        editButton.addActionListener(e -> {
            if (table.getSelectedRow() < 0) {
                return;
            }
            for (Person person : personList.getPersonList()) {
                if (samePersonInListAndTable(person)) {
                    Person newPerson = createNewPersonFromInput();
                    personList.changePerson(person, newPerson);
                    updateTableModel();
                    break;
                }
            }
        });

        // action for deleteButton
        deleteButton.addActionListener(e -> {
            if (table.getSelectedRow() < 0) {
                return;
            }
            Person personToBeRemoved = null;
            for (Person person : personList.getPersonList()) {
                if (samePersonInListAndTable(person)) {
                    personToBeRemoved = person;
                    break;
                }
            }
            personList.deletePerson(personToBeRemoved);
            tableModel.removeRow(table.getSelectedRow());
        });

        // finalize the JFrame
        frame.pack();
        frame.setSize(600, 600);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getSize().width) / 2;
        int y = (screenSize.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);

        showPopUpIntroduction();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LogPrinter logPrinter = new LogPrinter();
                e.getWindow().dispose();
            }
        });
    }

    // MODIFIES: personList, tableModel
    // EFFECTS: copy the values from personList to tableModel
    private void updateTableModel() {
        String[][] newPersonArray = new String[personList.getPersonList().size()][4];
        int row = 0;
        for (Person person : personList.getPersonList()) {
            newPersonArray[row][0] = person.getName();
            newPersonArray[row][1] = person.getGender();
            newPersonArray[row][2] = person.getHairColor();
            newPersonArray[row][3] = person.getWhereMet();
            row++;
        }
        tableModel.setRowCount(row);
        for (int row2 = 0; row2 < row; row2++) {
            for (int col = 0; col < 4; col++) {
                tableModel.setValueAt(newPersonArray[row2][col], row2, col);
            }
        }
    }

    // EFFECTS: return a new person with inputs from dialog windows,
    //          return null if cancel is pressed on any of the dialogs
    private Person createNewPersonFromInput() {
        JFrame inputFrame = new JFrame();
        String name = JOptionPane.showInputDialog(inputFrame,"Enter Name");
        if (name == null) {
            return null;
        }
        String gender = JOptionPane.showInputDialog(inputFrame,"Enter Gender");
        if (gender == null) {
            return null;
        }
        String hairColor = JOptionPane.showInputDialog(inputFrame,"Enter Hair Color");
        if (hairColor == null) {
            return null;
        }
        String whereMet = JOptionPane.showInputDialog(inputFrame,"Enter Place of Encounter");
        if (whereMet == null) {
            return null;
        }
        return new Person(name, gender, hairColor, whereMet);
    }

    // EFFECTS: returns true if the given person is the same person as the person selected in the JTable
    private boolean samePersonInListAndTable(Person person) {
        boolean sameName =
                tableModel.getValueAt(table.getSelectedRow(), 0).equals(person.getName());
        boolean sameGender =
                tableModel.getValueAt(table.getSelectedRow(), 1).equals(person.getGender());
        boolean sameHairColor =
                tableModel.getValueAt(table.getSelectedRow(), 2).equals(person.getHairColor());
        boolean sameWhereMet =
                tableModel.getValueAt(table.getSelectedRow(), 3).equals(person.getWhereMet());
        return (sameName && sameGender && sameHairColor && sameWhereMet);
    }

    // copied and modified from https://stackoverflow.com/questions/29636217/how-to-have-an-image-pop-up-in-java
    // EFFECTS: show a pop-up window with the introduction image
    private void showPopUpIntroduction() {
        JFrame popUpMeme = new JFrame("Don't let this happen to you again!");
        ImageIcon memeImage = new ImageIcon("data/forgetNameMeme.jpg");
        JLabel memeLabel = new JLabel(memeImage);
        popUpMeme.add(memeLabel);
        popUpMeme.setSize(memeImage.getIconWidth(), memeImage.getIconHeight());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - popUpMeme.getSize().width) / 2;
        int y = (screenSize.height - popUpMeme.getSize().height) / 2;
        popUpMeme.setLocation(x, y);
        popUpMeme.setVisible(true);
    }


}
