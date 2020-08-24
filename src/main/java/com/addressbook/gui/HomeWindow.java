package com.addressbook.gui;

import com.addressbook.controller.AppController;
import com.addressbook.entity.Person;
import com.addressbook.utils.GUIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeWindow {

    private static final TableView<Person> personsTable = new TableView<>();
    private static final String style = "-fx-font-weight:normal; -fx-color: #f0f0f0; -fx-font-size:11; -fx-font-family: Verdana;";
    private static Button[] buttons;
    private static TextField[] textFields;
    private static Integer index = 0;

    private static final AppController controller = new AppController();

    public static void show() {
        Stage homeWindow = new Stage();
        homeWindow.setTitle("Address Book Manager");
        homeWindow.setOnCloseRequest(e -> {
            AppController.session.close();
            homeWindow.close();
        });
        String[] buttonCaptions = new String[]{"Add New", "Update", "Delete", "Show emails", "Show phones", "|<", "<<", ">>", ">|"};
        buttons = new Button[buttonCaptions.length];
        String[] labels = new String[]{"Person ID", "First Name", "Last Name"};
        textFields = new TextField[3];
        BorderPane border = GUIUtils.makeBorder(personsTable,buttons, buttonCaptions, labels,textFields);
        setOnActionForHomeWindowButtons();
        populatePersonsForm(0);
        populatePersonsTable();
        homeWindow.setScene(new Scene(border, 1000, 650));
        homeWindow.show();
    }

    private static void setOnActionForHomeWindowButtons(){
        for (Button button : buttons) {
            button.setOnAction(new PersonsButtonHandler());
        }
    }

    private static class PersonsButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource().equals(buttons[0])) {
                // Button "Add New"
                Person person = new Person(999, textFields[1].getText(), textFields[2].getText());
                controller.addPerson(person);
            } else if (event.getSource().equals(buttons[1])) {
                // Button "Update"
                Person person = new Person(Integer.parseInt(textFields[0].getText()),textFields[1].getText(), textFields[2].getText());
                controller.updatePerson(person);
            } else if (event.getSource().equals(buttons[2])) {
                // Button "Delete"
                Person person = controller.getPersonList().get(index);
                controller.removePerson(person.getPerson_id());
                index = 0;
            } else if (event.getSource().equals(buttons[3])) {
                // Button "Show emails"
                Person person = new Person(Integer.parseInt(textFields[0].getText()),textFields[1].getText(), textFields[2].getText());
                EmailsWindow.show(person);
            } else if (event.getSource().equals(buttons[4])) {
                // Button "Show phones"
                Person person = new Person(Integer.parseInt(textFields[0].getText()), textFields[1].getText(), textFields[2].getText());
                PhonesWindow.show(person);
            } else if (event.getSource().equals(buttons[5])) {
                // Button "Go to first"
                index = 0;
            }
            else if (event.getSource().equals(buttons[6])) {
                // Button "Go to previous"
                if (index > 0) {
                    index--;
                } else {
                    event.consume();
                }
            } else if (event.getSource().equals(buttons[7])) {
                // Button "Go to next"
                if (index < controller.getPersonList().size() - 1) {
                    index++;
                } else {
                    event.consume();
                }
            } else if (event.getSource().equals(buttons[8])) {
                // Button "Go to last"
                index = controller.getPersonList().size() - 1;
            }
            populatePersonsForm(index);
            populatePersonsTable();
        }
    }

    private static void populatePersonsForm(int index) {
        if (controller.getPersonList().isEmpty())
            return;
        Person person = controller.getPersonList().get(index);
        textFields[0].setText(person.getPerson_id().toString());
        textFields[1].setText(person.getFirstName());
        textFields[2].setText(person.getLastName());
    }

    private static void populatePersonsTable() {
        personsTable.getItems().clear();
        personsTable.setStyle(style);
        personsTable.setItems(controller.getPersonList());
        TableColumn<Person, Integer> personIdCol = new TableColumn<>("Person ID");
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("person_id"));
        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        personsTable.getColumns().setAll(personIdCol, firstNameCol, lastNameCol);
    }


}
