package com.addressbook.app;

import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIController {

    String bgcolor = "-fx-background-color: #f0f0f0";
    String style = "-fx-font-weight:normal; -fx-color: #f0f0f0; -fx-font-size:11; -fx-font-family: Verdana;";

    private TableView<Person> table = new TableView<>();
    private String buttonCaption[] = {"Add New", "Update", "Delete", "Show emails", "Show phones","|<", "<<", ">>", ">|"};
    private String label[] = { "Person ID", "First Name", "Last Name"};
    private String fields[] = { "personId", "first_name", "last_name"};
    private Button button[] = new Button[9];
    private TextField textField[] = new TextField[3];

    private AppController controller = new AppController();
    private static int  index;

    public void main (Stage stage) {
        stage.setTitle("Address Book");
        BorderPane border = new BorderPane();
        border.setTop(createButtonBox());
        border.setCenter(createForm());
        border.setBottom(table);
        border.setStyle(bgcolor);
        border.setPadding(new Insets(10, 10, 10, 10));
        populateForm(0);
        populateTable();
        stage.setScene(new Scene(border, 800, 650));
        stage.show();
    }

    private Pane createForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(20);
        grid.setStyle(style);
        grid.setVgap(2);
        for (int i = 0; i < label.length; i++) {
            grid.add(new Label(label[i] + " :"), 1, i);
            textField[i] = new TextField();
            grid.add(textField[i], 2, i);
        }
        textField[0].setEditable(false);
        textField[0].setTooltip(new Tooltip("This field is automatically generated hence not editable"));
        return grid;
    }

    private Pane createButtonBox() {
        int width = 100;
        HBox box = new HBox();
        box.setAlignment(Pos. CENTER);
        box.setSpacing(5);

        for (int i = 0; i < buttonCaption.length; i++) {
            button[i] = new Button(buttonCaption[i]);
            button[i].setStyle(style);
            button[i].setMinWidth(width);
            button[i].setOnAction(new ButtonHandler());
            box.getChildren().add(button[i]);
        }
        return box;
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource().equals(button[0])) {
                // Buton "Add New"
                Person person = new Person(999, textField[1].getText(), textField[2].getText());
                controller.addPerson(person);
            } else if (event.getSource().equals(button[1])) {
                // Buton "Update"
                Person person = new Person(Integer.parseInt(textField[0].getText()),textField[1].getText(), textField[2].getText());
                controller.updatePerson(person);
            } else if (event.getSource().equals(button[2])) {
                // Buton "Delete"
                Person person = (Person) controller.getPersonList().get(index);
                controller.removePerson(person.getPerson_id());
            } else if (event.getSource().equals(button[3])) {
                // Buton "Show emails"
               Person person = new Person(Integer.parseInt(textField[0].getText()),textField[1].getText(), textField[2].getText());
               populateEmailsView(person);
            } else if (event.getSource().equals(button[6])) {
                // Buton "Go to first"
                if (index > 0) {
                    index--;
                } else
                    event.consume();
            } else if (event.getSource().equals(button[5])) {
                index = 0;
            } else if (event.getSource().equals(button[7])) {
                if (index < controller.getPersonList().size() - 1) {
                    index++;
                } else
                    event.consume();
            } else if (event.getSource().equals(button[8])) {
                index = controller.getPersonList().size() - 1;
            }
            populateForm(index);
            populateTable();
        }
    }

    private void populateForm(int i) {
        if (controller.getPersonList().isEmpty())
            return;
        Person person = (Person) controller.getPersonList().get(i);
        textField[0].setText(person.getPerson_id().toString());
        textField[1].setText(person.getFirstName());
        textField[2].setText(person.getLastName());
    }

    private void populateTable() {
        table.getItems().clear();
        table.setStyle(style);
        table.setItems(controller.getPersonList());
        TableColumn<Person, Integer> personIdCol = new TableColumn<Person, Integer>("Person ID");
        personIdCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
        TableColumn<Person, String> firstNameCol = new TableColumn<Person, String>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        TableColumn<Person, String> lastNameCol = new TableColumn<Person, String>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        table.getColumns().setAll(personIdCol, firstNameCol, lastNameCol);
    }

    private void populateEmailsView(Person person) {
        Stage emailStage = new Stage();
        emailStage.setTitle(person.getFirstName() + " " + person.getLastName() + " | Emails");
        BorderPane border = new BorderPane();
        TableView<Email> emailTable = new TableView<>();
        border.setBottom(emailTable);
        border.setStyle(bgcolor);
        border.setPadding(new Insets(10, 10, 10, 10));

        emailTable.getItems().clear();
        emailTable.setStyle(style);
        emailTable.setItems(controller.getEmailList(person.getPerson_id()));
        TableColumn<Email, Integer> emailIdCol = new TableColumn<Email, Integer>("Email ID");
        emailIdCol.setCellValueFactory(new PropertyValueFactory<Email, Integer>("emailId"));
        TableColumn<Email, String> emailAddressCol = new TableColumn<Email, String>("Email Address");
        emailAddressCol.setCellValueFactory(new PropertyValueFactory<Email, String>("address"));
        emailTable.getColumns().setAll(emailIdCol, emailAddressCol);

        emailStage.setScene(new Scene(border, 800, 650));
        emailStage.show();
    }

}
