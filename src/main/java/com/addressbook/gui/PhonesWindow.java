package com.addressbook.gui;

import com.addressbook.controller.AppController;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import com.addressbook.utils.GUIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PhonesWindow {

    private static final TableView<Phone> phonesTable = new TableView<>();
    private static final String style = "-fx-font-weight:normal; -fx-color: #f0f0f0; -fx-font-size:11; -fx-font-family: Verdana;";
    private static Button[] buttons;
    private static TextField[] textFields;
    private static Integer index = 0;
    private static Integer selectedPersonId;

    private static final AppController controller = new AppController();

    public static void show(Person selectedPerson){
        selectedPersonId = selectedPerson.getPerson_id();
        Stage phonesWindow = new Stage();
        phonesWindow.initModality(Modality.APPLICATION_MODAL);
        phonesWindow.setTitle(selectedPerson.getFirstName() + " " + selectedPerson.getLastName() + " | Phones");
        String[] buttonCaptions = new String[]{"Add New", "Update", "Delete", "|<", "<<", ">>", ">|"};
        buttons = new Button[buttonCaptions.length];
        String[] labels = new String[]{"Phone ID", "Phone number"};
        textFields = new TextField[2];
        BorderPane border = GUIUtils.makeBorder(phonesTable,buttons, buttonCaptions, labels,textFields);
        setOnActionForPhonesWindowButtons();
        populatePhonesForm(selectedPerson.getPerson_id(), 0);
        populatePhonesTable(selectedPerson.getPerson_id());
        phonesWindow.setScene(new Scene(border, 800, 650));
        phonesWindow.showAndWait();
    }

    private static void setOnActionForPhonesWindowButtons(){
        for (Button button : buttons) {
            button.setOnAction(new PhonesButtonHandler());
        }
    }

    private static class PhonesButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource().equals(buttons[0])) {
                // Button "Add New"
                Phone phone = new Phone(999, textFields[1].getText());
                controller.addPhone(selectedPersonId, phone);
            } else if (event.getSource().equals(buttons[1])) {
                // Button "Update"
                Phone phone = new Phone(Integer.parseInt(textFields[0].getText()), textFields[1].getText());
                controller.updatePhone(phone);
            } else if (event.getSource().equals(buttons[2])) {
                // Button "Delete"
                Integer phoneId = Integer.parseInt(textFields[0].getText());
                controller.removePhone(selectedPersonId, phoneId);
                index = 0;
            } else if (event.getSource().equals(buttons[3])) {
                // Button "Go to first"
                index = 0;
            } else if (event.getSource().equals(buttons[4])) {
                // Button "Go to previous"
                if (index > 0) {
                    index--;
                } else {
                    event.consume();
                }
            } else if (event.getSource().equals(buttons[5])) {
                // Button "Go to next"
                if (index < controller.getPhoneList(selectedPersonId).size() - 1) {
                    index++;
                } else {
                    event.consume();
                }
            } else if (event.getSource().equals(buttons[6])) {
                // Button "Go to last"
                index = controller.getPhoneList(selectedPersonId).size() - 1;
            }
            populatePhonesForm(selectedPersonId, index);
            populatePhonesTable(selectedPersonId);
        }
    }

    private static void populatePhonesForm(Integer personId, int index) {
        if (controller.getPhoneList(personId).isEmpty())
            return;
        Phone phone = controller.getPhoneList(personId).get(index);
        textFields[0].setText(phone.getPhoneId().toString());
        textFields[1].setText(phone.getNumber());
    }

    private static void populatePhonesTable(Integer personId) {
        phonesTable.getItems().clear();
        phonesTable.setStyle(style);
        phonesTable.setItems(controller.getPhoneList(personId));
        TableColumn<Phone, Integer> phoneIdCol = new TableColumn<>("Phone ID");
        phoneIdCol.setCellValueFactory(new PropertyValueFactory<>("phoneId"));
        TableColumn<Phone, String> phoneNumberCol = new TableColumn<>("Phone#");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        phonesTable.getColumns().setAll(phoneIdCol, phoneNumberCol);
    }

}
