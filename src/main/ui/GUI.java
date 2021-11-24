package ui;

import model.Person;
import model.PersonList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GUI {
    private PersonList personList = new PersonList();

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

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        String[] columnNames = {"Name", "Gender", "Hair Color", "Place of Encounter"};
        String[][] data = {};

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);

        // add the jTable to the scrollPane, and the scrollPane to the JPanel
        JScrollPane scrollPane = new JScrollPane(table);
        gridBagConstraints.gridy = 1;
        panel.add(scrollPane, gridBagConstraints);

        // add the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
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
            JFrame inputFrame = new JFrame();
            String name = JOptionPane.showInputDialog(inputFrame,"Enter Name");
            String gender = JOptionPane.showInputDialog(inputFrame,"Enter Gender");
            String hairColor = JOptionPane.showInputDialog(inputFrame,"Enter Hair Color");
            String whereMet = JOptionPane.showInputDialog(inputFrame,"Enter Place of Encounter");
            Person newPerson = new Person(name, gender, hairColor, whereMet);
            personList.addPerson(newPerson);

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
        });

        // action for editButton
        editButton.addActionListener(e -> {
            if (table.getSelectedRow() < 0) {
                return;
            }
            for (Person person : personList.getPersonList()) {
                boolean sameName =
                        tableModel.getValueAt(table.getSelectedRow(), 0).equals(person.getName());
                boolean sameGender =
                        tableModel.getValueAt(table.getSelectedRow(), 1).equals(person.getGender());
                boolean sameHairColor =
                        tableModel.getValueAt(table.getSelectedRow(), 2).equals(person.getHairColor());
                boolean sameWhereMet =
                        tableModel.getValueAt(table.getSelectedRow(), 3).equals(person.getWhereMet());
                if (sameName && sameGender && sameHairColor && sameWhereMet) {
                    JFrame inputFrame = new JFrame();
                    String name = JOptionPane.showInputDialog(inputFrame,"Enter Name");
                    String gender = JOptionPane.showInputDialog(inputFrame,"Enter Gender");
                    String hairColor = JOptionPane.showInputDialog(inputFrame,"Enter Hair Color");
                    String whereMet = JOptionPane.showInputDialog(inputFrame,"Enter Place of Encounter");
                    person.modifyPerson(name, gender, hairColor, whereMet);
                    tableModel.setValueAt(name, table.getSelectedRow(), 0);
                    tableModel.setValueAt(gender, table.getSelectedRow(), 1);
                    tableModel.setValueAt(hairColor, table.getSelectedRow(), 2);
                    tableModel.setValueAt(whereMet, table.getSelectedRow(), 3);
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
                boolean sameName =
                        tableModel.getValueAt(table.getSelectedRow(), 0).equals(person.getName());
                boolean sameGender =
                        tableModel.getValueAt(table.getSelectedRow(), 1).equals(person.getGender());
                boolean sameHairColor =
                        tableModel.getValueAt(table.getSelectedRow(), 2).equals(person.getHairColor());
                boolean sameWhereMet =
                        tableModel.getValueAt(table.getSelectedRow(), 3).equals(person.getWhereMet());
                if (sameName && sameGender && sameHairColor && sameWhereMet) {
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
    }
}
