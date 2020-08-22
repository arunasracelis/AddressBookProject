package com.addressbook.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GUIUtils {

    private static final String style = "-fx-font-weight:normal; -fx-color: #f0f0f0; -fx-font-size:11; -fx-font-family: Verdana;";
    private static final String backgroundColor = "-fx-background-color: #f0f0f0";

    public static <T> BorderPane makeBorder(TableView<T> tableView, Button[] buttons, String[] buttonCaptions, String[] labels, TextField[] textFields){
        BorderPane border = new BorderPane();
        border.setTop(createButtonBox(buttons, buttonCaptions));
        border.setCenter(createForm(labels, textFields));
        border.setBottom(tableView);
        border.setStyle(backgroundColor);
        border.setPadding(new Insets(10, 10, 10, 10));
        return border;
    }

    private static Pane createButtonBox(Button[] buttons, String[] buttonCaptions) {
        int width = 100;
        HBox box = new HBox();
        box.setAlignment(Pos. CENTER);
        box.setSpacing(5);
        for (int i = 0; i < buttonCaptions.length; i++) {
            buttons[i] = new Button(buttonCaptions[i]);
            buttons[i].setStyle(style);
            buttons[i].setMinWidth(width);
            box.getChildren().add(buttons[i]);
        }
        return box;
    }

    private static Pane createForm(String[] labels, TextField[] textFields) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(20);
        grid.setStyle(style);
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
