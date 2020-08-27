package com.addressbook.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GUIUtils {

    public static final String STYLE = "-fx-font-weight:normal; -fx-color: #f0f0f0; -fx-font-size:11; -fx-font-family: Verdana;";
    private static final String BACKGROUND_COLOR = "-fx-background-color: #f0f0f0";
    public static final String[] HOME_WINDOW_FORM_LABELS = new String[]{"Person ID", "First Name", "Last Name"};
    public static final String[] EMAILS_WINDOW_FORM_LABELS = new String[]{"Email ID", "Email address"};
    public static final String[] PHONES_WINDOW_FORM_LABELS = new String[]{"Phone ID", "Phone number"};
    public static final String[] HOME_WINDOW_BUTTON_CAPTIONS = new String[]{"Add New", "Update", "Delete", "Show emails", "Show phones", "|<", "<<", ">>", ">|"};
    public static final String[] OTHER_WINDOW_BUTTON_CAPTIONS = new String[]{"Add New", "Update", "Delete", "|<", "<<", ">>", ">|"};

    public static <T> BorderPane makeBorder(Pane buttonBox, Pane form, TableView<T> tableView){
        BorderPane border = new BorderPane();
        border.setTop(buttonBox);
        border.setCenter(form);
        border.setBottom(tableView);
        border.setStyle(BACKGROUND_COLOR);
        border.setPadding(new Insets(10, 10, 10, 10));
        return border;
    }

    public static Pane createButtonBox(Button[] buttons, String[] buttonCaptions) {
        int width = 100;
        HBox box = new HBox();
        box.setAlignment(Pos. CENTER);
        box.setSpacing(5);
        for (int i = 0; i < buttonCaptions.length; i++) {
            buttons[i] = new Button(buttonCaptions[i]);
            buttons[i].setStyle(STYLE);
            buttons[i].setMinWidth(width);
            box.getChildren().add(buttons[i]);
        }
        return box;
    }

    public static Pane createForm(TextField[] textFields, String[] labels) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(20);
        grid.setStyle(STYLE);
        grid.setVgap(2);
        for (int i = 0; i < labels.length; i++) {
            grid.add(new Label(labels[i] + " :"), 1, i);
            textFields[i] = new TextField();
            textFields[i].setPrefWidth(300);
            grid.add(textFields[i], 2, i);
        }
        textFields[0].setEditable(false);
        textFields[0].setTooltip(new Tooltip("This field is automatically generated. It is not editable."));
        return grid;
    }

}
