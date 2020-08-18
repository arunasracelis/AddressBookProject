package com.addressbook.gui;

import com.addressbook.app.AppController;
import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.addressbook.gui.HomeWindow.selectedPersonId;

public class EmailsWindow {

    private static TableView<Email> emailsTable = new TableView<>();
    private static final String style = "-fx-font-weight:normal; -fx-color: #f0f0f0; -fx-font-size:11; -fx-font-family: Verdana;";
    private static Button[] buttons;
    private static TextField[] textFields;
    private static Integer index = 0;

    private static AppController controller = new AppController();

    public static void show(Person person){
        Stage emailsWindow = new Stage();
        emailsWindow.initModality(Modality.APPLICATION_MODAL);
        emailsWindow.setTitle(person.getFirstName() + " " + person.getLastName() + " | Emails");
        String[] buttonCaptions = new String[]{"Add New", "Update", "Delete", "|<", "<<", ">>", ">|"};
        buttons = new Button[buttonCaptions.length];
        String[] labels = new String[]{"Email ID", "Email address"};
        textFields = new TextField[labels.length];
        BorderPane border = GUIUtils.makeBorder(emailsTable,buttons, buttonCaptions, labels,textFields);
        setOnActionForEmailsWindowButtons();
        populateEmailsForm(person.getPerson_id(), 0);
        populateEmailsTable(person.getPerson_id());
        emailsWindow.setScene(new Scene(border, 800, 650));
        emailsWindow.showAndWait();
    }

    private static void setOnActionForEmailsWindowButtons(){
        for (Button button : buttons) {
            button.setOnAction(new EmailsButtonHandler());
        }
    }

    private static class EmailsButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource().equals(buttons[0])) {
                // Button "Add New"
                Email email = new Email(999, textFields[1].getText());
                controller.addEmail(selectedPersonId, email);
            } else if (event.getSource().equals(buttons[1])) {
                // Button "Update"
                Email email = new Email(Integer.parseInt(textFields[0].getText()), textFields[1].getText());
                controller.updateEmail(email);
            } else if (event.getSource().equals(buttons[2])) {
                // Button "Delete"
                Integer emailId = Integer.parseInt(textFields[0].getText());
                controller.removeEmail(selectedPersonId, emailId);
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
                if (index < controller.getEmailList(selectedPersonId).size() - 1) {
                    index++;
                } else {
                    event.consume();
                }
            } else if (event.getSource().equals(buttons[6])) {
                // Button "Go to last"
                index = controller.getEmailList(selectedPersonId).size() - 1;
            }
            populateEmailsForm(selectedPersonId, index);
            populateEmailsTable(selectedPersonId);
        }
    }

    private static void populateEmailsForm(Integer personId, int index) {
        if (controller.getEmailList(personId).isEmpty())
            return;
        Email email = controller.getEmailList(personId).get(index);
        textFields[0].setText(email.getEmailId().toString());
        textFields[1].setText(email.getAddress());
    }

    private static void populateEmailsTable(Integer personId) {
        emailsTable.getItems().clear();
        emailsTable.setStyle(style);
        emailsTable.setItems(controller.getEmailList(personId));
        TableColumn<Email, Integer> emailIdCol = new TableColumn<>("Email ID");
        emailIdCol.setCellValueFactory(new PropertyValueFactory<>("emailId"));
        TableColumn<Email, String> emailAddressCol = new TableColumn<>("Email Address");
        emailAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailsTable.getColumns().setAll(emailIdCol, emailAddressCol);
    }

}
