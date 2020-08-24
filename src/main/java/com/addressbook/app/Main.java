package com.addressbook.app;

import com.addressbook.service.GUIService;
import com.addressbook.service.GUIServiceImpl;
import com.addressbook.utils.HibernateUtils;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final GUIService guiService = new GUIServiceImpl();
    private static HibernateUtils hibernateUtils = new HibernateUtils();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //DemoData.load();
        guiService.showHomeWindow();
    }

}
