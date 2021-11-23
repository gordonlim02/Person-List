package ui;

import com.sun.javafx.fxml.LoadListener;
import model.Person;
import model.PersonList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GUI {
    private PersonList personList = new PersonList();

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public GUI() throws IOException {
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
        String[][] data = {
                { "Monica", "Female", "Brown", "Central Perk"},
                { "Jeff", "Male", "Brown", "Greendale" },
        };
        Person kundanKumarJha = new Person("Kundan Kumar Jha", "4031", "CSE", "o");
        Person anandJha = new Person("Anand Jha", "6014", "IT", "b");
        personList.addPerson(kundanKumarJha);
        personList.addPerson(anandJha);
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);

        // add the jTable to the scrollPane, and the scrollPane to the JPanel
        JScrollPane scrollPane = new JScrollPane(table);
        gridBagConstraints.gridx = 0;
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
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        // action for saveButton
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JsonWriter jsonWriter = new JsonWriter("./data/personList.json");
                try {
                    jsonWriter.open();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                jsonWriter.write(personList);
                jsonWriter.close();
            }
        });

        // action for addButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        // action for clicking on a cell
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // action for editButton
                editButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO: need to know how to delete a person from personList, given the selected row
                    }
                });
            }

        });

        // finalize the JFrame
        frame.pack();
        frame.setSize(600, 600);
    }

    private void updateTableModel(TableModel tableModel, PersonList personList) {

    }

}
