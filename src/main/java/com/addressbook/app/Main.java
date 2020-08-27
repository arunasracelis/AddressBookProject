package com.addressbook.app;

import com.addressbook.controller.GUIController;
import com.addressbook.utils.DemoData;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final GUIController guiController = new GUIController();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //DemoData.load();
        guiController.showHomeWindow();
    }

}
