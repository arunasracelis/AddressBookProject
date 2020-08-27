package com.addressbook.gui;

import com.addressbook.controller.AppController;
import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import com.addressbook.utils.GUIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.addressbook.utils.GUIUtils.*;

public class PhonesWindow {

    private static final TableView<Phone> phonesTable = new TableView<>();
    private static TextField[] phonesWindowTextFields = new TextField[PHONES_WINDOW_FORM_LABELS.length];
    private static Button[] phonesWindowButtons = new Button[OTHER_WINDOW_BUTTON_CAPTIONS.length];
    private static Integer phoneIndex = 0;
    private static Integer selectedPersonId;

    private static final AppController controller = new AppController();

    public static void show(Person selectedPerson){
        selectedPersonId = selectedPerson.getPerson_id();
        Stage phonesWindow = new Stage();
        phonesWindow.initModality(Modality.APPLICATION_MODAL);
        phonesWindow.setTitle(selectedPerson.getFirstName() + " " + selectedPerson.getLastName() + " | Phones");
        Pane phonesWindowButtonBox = createButtonBox(phonesWindowButtons, OTHER_WINDOW_BUTTON_CAPTIONS);
        Pane phonesForm = createForm(phonesWindowTextFields, PHONES_WINDOW_FORM_LABELS);
        BorderPane border = GUIUtils.makeBorder(phonesWindowButtonBox, phonesForm, phonesTable);
        setHandlerForPhonesWindowButtons();
        populatePhonesForm(selectedPerson.getPerson_id(), 0);
        populatePhonesTable(selectedPerson.getPerson_id());
        setCellValueFromTableToTextField();
        phonesWindow.setScene(new Scene(border, 800, 650));
        phonesWindow.showAndWait();
    }

    private static void setHandlerForPhonesWindowButtons(){
        for (Button button : phonesWindowButtons) {
            button.setOnAction(new PhonesButtonHandler());
        }
    }

    private static class PhonesButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource().equals(phonesWindowButtons[0])) {
                // Button "Add New"
                Phone phone = new Phone(999, phonesWindowTextFields[1].getText());
                controller.addPhone(selectedPersonId, phone);
                phoneIndex = controller.getPhoneList(selectedPersonId).size() - 1;
            } else if (event.getSource().equals(phonesWindowButtons[1])) {
                // Button "Update"
                calculateIndex();
                Phone phone = new Phone(Integer.parseInt(phonesWindowTextFields[0].getText()), phonesWindowTextFields[1].getText());
                controller.updatePhone(phone);
            } else if (event.getSource().equals(phonesWindowButtons[2])) {
                // Button "Delete"
                Integer phoneId = Integer.parseInt(phonesWindowTextFields[0].getText());
                controller.removePhone(selectedPersonId, phoneId);
                phoneIndex = 0;
            } else if (event.getSource().equals(phonesWindowButtons[3])) {
                // Button "Go to first"
                phoneIndex = 0;
            } else if (event.getSource().equals(phonesWindowButtons[4])) {
                // Button "Go to previous"
                if (phoneIndex > 0) {
                    phoneIndex--;
                } else {
                    event.consume();
                }
            } else if (event.getSource().equals(phonesWindowButtons[5])) {
                // Button "Go to next"
                if (phoneIndex < controller.getPhoneList(selectedPersonId).size() - 1) {
                    phoneIndex++;
                } else {
                    event.consume();
                }
            } else if (event.getSource().equals(phonesWindowButtons[6])) {
                // Button "Go to last"
                phoneIndex = controller.getPhoneList(selectedPersonId).size() - 1;
            }
            populatePhonesForm(selectedPersonId, phoneIndex);
            populatePhonesTable(selectedPersonId);
        }
    }

    private static void populatePhonesForm(Integer personId, int index) {
        if (controller.getPhoneList(personId).isEmpty())
            return;
        Phone phone = controller.getPhoneList(personId).get(index);
        phonesWindowTextFields[0].setText(phone.getPhoneId().toString());
        phonesWindowTextFields[1].setText(phone.getNumber());
    }

    private static void populatePhonesTable(Integer personId) {
        phonesTable.getItems().clear();
        phonesTable.setStyle(STYLE);
        phonesTable.setItems(controller.getPhoneList(personId));
        TableColumn<Phone, Integer> phoneIdCol = new TableColumn<>("Phone ID");
        phoneIdCol.setCellValueFactory(new PropertyValueFactory<>("phoneId"));
        TableColumn<Phone, String> phoneNumberCol = new TableColumn<>("Phone#");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        phonesTable.getColumns().setAll(phoneIdCol, phoneNumberCol);
    }

    private static void setCellValueFromTableToTextField(){
        phonesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                phoneIndex = phonesTable.getSelectionModel().getSelectedIndex();
                Phone selectedPhone = phonesTable.getItems().get(phoneIndex);
                phonesWindowTextFields[0].setText(selectedPhone.getPhoneId().toString());
                phonesWindowTextFields[1].setText(selectedPhone.getNumber());
            }
        });
    }

    private static void calculateIndex(){
        int selectedIndex = phonesTable.getSelectionModel().getSelectedIndex();
        if (phoneIndex == 0 && selectedIndex >= 0) {
            phoneIndex = selectedIndex;
        }
    }


}
