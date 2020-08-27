package com.addressbook.gui;

import com.addressbook.controller.AppController;
import com.addressbook.entity.Email;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.addressbook.utils.GUIUtils.*;

public class EmailsWindow {

    private static final TableView<Email> emailsTable = new TableView<>();
    private static TextField[] emailsWindowTextFields = new TextField[EMAILS_WINDOW_FORM_LABELS.length];
    private static Button[] emailsWindowButtons = new Button[OTHER_WINDOW_BUTTON_CAPTIONS.length];
    private static Integer emailIndex = 0;
    private static Integer selectedPersonId;

    private static final AppController controller = new AppController();

    public static void show(Person selectedPerson){
        selectedPersonId = selectedPerson.getPerson_id();
        Stage emailsWindow = new Stage();
        emailsWindow.initModality(Modality.APPLICATION_MODAL);
        emailsWindow.setTitle(selectedPerson.getFirstName() + " " + selectedPerson.getLastName() + " | Emails");
        Pane emailsWindowButtonBox = createButtonBox(emailsWindowButtons, OTHER_WINDOW_BUTTON_CAPTIONS);
        Pane emailsForm = createForm(emailsWindowTextFields, EMAILS_WINDOW_FORM_LABELS);
        BorderPane border = GUIUtils.makeBorder(emailsWindowButtonBox, emailsForm, emailsTable);
        setHandlerForEmailsWindowButtons();
        populateEmailsForm(selectedPerson.getPerson_id(), 0);
        populateEmailsTable(selectedPerson.getPerson_id());
        setCellValueFromTableToTextField();
        emailsWindow.setScene(new Scene(border, 800, 650));
        emailsWindow.showAndWait();
    }

    private static void setHandlerForEmailsWindowButtons(){
        for (Button button : emailsWindowButtons) {
            button.setOnAction(new EmailsButtonHandler());
        }
    }

    private static class EmailsButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource().equals(emailsWindowButtons[0])) {
                // Button "Add New"
                Email email = new Email(999, emailsWindowTextFields[1].getText());
                controller.addEmail(selectedPersonId, email);
                emailIndex = controller.getEmailList(selectedPersonId).size() - 1;
            } else if (event.getSource().equals(emailsWindowButtons[1])) {
                // Button "Update"
                calculateIndex();
                Email email = new Email(Integer.parseInt(emailsWindowTextFields[0].getText()), emailsWindowTextFields[1].getText());
                controller.updateEmail(email);
            } else if (event.getSource().equals(emailsWindowButtons[2])) {
                // Button "Delete"
                Integer emailId = Integer.parseInt(emailsWindowTextFields[0].getText());
                controller.removeEmail(selectedPersonId, emailId);
                emailIndex = 0;
            } else if (event.getSource().equals(emailsWindowButtons[3])) {
                // Button "Go to first"
                emailIndex = 0;
            } else if (event.getSource().equals(emailsWindowButtons[4])) {
                // Button "Go to previous"
                if (emailIndex > 0) {
                    emailIndex--;
                } else {
                    event.consume();
                }
            } else if (event.getSource().equals(emailsWindowButtons[5])) {
                // Button "Go to next"
                if (emailIndex < controller.getEmailList(selectedPersonId).size() - 1) {
                    emailIndex++;
                } else {
                    event.consume();
                }
            } else if (event.getSource().equals(emailsWindowButtons[6])) {
                // Button "Go to last"
                emailIndex = controller.getEmailList(selectedPersonId).size() - 1;
            }
            populateEmailsForm(selectedPersonId, emailIndex);
            populateEmailsTable(selectedPersonId);
        }
    }

    private static void populateEmailsForm(Integer personId, int index) {
        if (controller.getEmailList(personId).isEmpty())
            return;
        Email email = controller.getEmailList(personId).get(index);
        emailsWindowTextFields[0].setText(email.getEmailId().toString());
        emailsWindowTextFields[1].setText(email.getAddress());
    }

    private static void populateEmailsTable(Integer personId) {
        emailsTable.getItems().clear();
        emailsTable.setStyle(STYLE);
        emailsTable.setItems(controller.getEmailList(personId));
        TableColumn<Email, Integer> emailIdCol = new TableColumn<>("Email ID");
        emailIdCol.setCellValueFactory(new PropertyValueFactory<>("emailId"));
        TableColumn<Email, String> emailAddressCol = new TableColumn<>("Email Address");
        emailAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailsTable.getColumns().setAll(emailIdCol, emailAddressCol);
    }

    private static void setCellValueFromTableToTextField(){
        emailsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                emailIndex = emailsTable.getSelectionModel().getSelectedIndex();
                Email selectedEmail = emailsTable.getItems().get(emailIndex);
                emailsWindowTextFields[0].setText(selectedEmail.getEmailId().toString());
                emailsWindowTextFields[1].setText(selectedEmail.getAddress());
            }
        });
    }

    private static void calculateIndex(){
        int selectedIndex = emailsTable.getSelectionModel().getSelectedIndex();
        if (emailIndex == 0 && selectedIndex >= 0) {
            emailIndex = selectedIndex;
        }
    }

}
