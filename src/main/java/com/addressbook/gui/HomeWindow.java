package com.addressbook.gui;

import com.addressbook.controller.AppController;
import com.addressbook.controller.GUIController;
import com.addressbook.entity.Person;
import com.addressbook.utils.GUIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static com.addressbook.utils.GUIUtils.*;

public class HomeWindow {

    private static TableView<Person> personsTable = new TableView<>();
    private static TextField[] homeWindowTextFields = new TextField[HOME_WINDOW_FORM_LABELS.length];
    private static Button[] homeWindowButtons = new Button[HOME_WINDOW_BUTTON_CAPTIONS.length];
    private static Integer personIndex = 0;

    private static final AppController controller = new AppController();
    private static final GUIController guiController = new GUIController();

    public static void show() {
        Stage homeWindow = new Stage();
        homeWindow.setTitle("Address Book Manager");
        homeWindow.setOnCloseRequest(e -> homeWindow.close());
        Pane homeWindowButtonBox = createButtonBox(homeWindowButtons, HOME_WINDOW_BUTTON_CAPTIONS);
        Pane personForm = createForm(homeWindowTextFields, HOME_WINDOW_FORM_LABELS);
        BorderPane border = GUIUtils.makeBorder(homeWindowButtonBox, personForm, personsTable);
        setHandlerForHomeWindowButtons();
        populatePersonsForm(0);
        populatePersonsTable();
        setCellValueFromTableToTextField();
        homeWindow.setScene(new Scene(border, 1000, 650));
        homeWindow.show();
    }

    private static void setHandlerForHomeWindowButtons(){
        for (Button button : homeWindowButtons) {
            button.setOnAction(new PersonsButtonHandler());
        }
    }

    private static class PersonsButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource().equals(homeWindowButtons[0])) {
                // Button "Add New"
                Person person = new Person(999, homeWindowTextFields[1].getText(), homeWindowTextFields[2].getText());
                controller.addPerson(person);
                personIndex = controller.getPersonList().size() - 1;
            } else if (event.getSource().equals(homeWindowButtons[1])) {
                // Button "Update"
                calculateIndex();
                Person person = new Person(Integer.parseInt(homeWindowTextFields[0].getText()), homeWindowTextFields[1].getText(), homeWindowTextFields[2].getText());
                controller.updatePerson(person);
            } else if (event.getSource().equals(homeWindowButtons[2])) {
                // Button "Delete"
                Person person = controller.getPersonList().get(personIndex);
                controller.removePerson(person.getPerson_id());
                personIndex = 0;
            } else if (event.getSource().equals(homeWindowButtons[3])) {
                // Button "Show emails"
                calculateIndex();
                Person person = new Person(Integer.parseInt(homeWindowTextFields[0].getText()), homeWindowTextFields[1].getText(), homeWindowTextFields[2].getText());
                guiController.showEmailsWindow(person);
            } else if (event.getSource().equals(homeWindowButtons[4])) {
                // Button "Show phones"
                calculateIndex();
                Person person = new Person(Integer.parseInt(homeWindowTextFields[0].getText()), homeWindowTextFields[1].getText(), homeWindowTextFields[2].getText());
                guiController.showPhonesWindow(person);
            } else if (event.getSource().equals(homeWindowButtons[5])) {
                // Button "Go to first"
                personIndex = 0;
            }
            else if (event.getSource().equals(homeWindowButtons[6])) {
                // Button "Go to previous"
                if (personIndex > 0) {
                    personIndex--;
                } else {
                    event.consume();
                }
            } else if (event.getSource().equals(homeWindowButtons[7])) {
                // Button "Go to next"
                if (personIndex < controller.getPersonList().size() - 1) {
                    personIndex++;
                } else {
                    event.consume();
                }
            } else if (event.getSource().equals(homeWindowButtons[8])) {
                // Button "Go to last"
                personIndex = controller.getPersonList().size() - 1;
            }
            populatePersonsForm(personIndex);
            populatePersonsTable();
        }
    }

    private static void populatePersonsForm(int index) {
        if (controller.getPersonList().isEmpty())
            return;
        Person person = controller.getPersonList().get(index);
        homeWindowTextFields[0].setText(person.getPerson_id().toString());
        homeWindowTextFields[1].setText(person.getFirstName());
        homeWindowTextFields[2].setText(person.getLastName());
    }

    private static void populatePersonsTable() {
        personsTable.getItems().clear();
        personsTable.setStyle(STYLE);
        personsTable.setItems(controller.getPersonList());
        TableColumn<Person, Integer> personIdCol = new TableColumn<>("Person ID");
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("person_id"));
        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        personsTable.getColumns().setAll(personIdCol, firstNameCol, lastNameCol);
    }

    private static void setCellValueFromTableToTextField(){
        personsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                personIndex = personsTable.getSelectionModel().getSelectedIndex();
                Person selectedPerson = personsTable.getItems().get(personIndex);
                homeWindowTextFields[0].setText(selectedPerson.getPerson_id().toString());
                homeWindowTextFields[1].setText(selectedPerson.getFirstName());
                homeWindowTextFields[2].setText(selectedPerson.getLastName());
            }
        });
    }

    private static void calculateIndex(){
        int selectedIndex = personsTable.getSelectionModel().getSelectedIndex();
        if (personIndex == 0 && selectedIndex >= 0) {
            personIndex = selectedIndex;
        }
    }

}
