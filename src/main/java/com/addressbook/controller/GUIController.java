package com.addressbook.controller;

import com.addressbook.entity.Person;
import com.addressbook.service.GUIService;
import com.addressbook.service.GUIServiceImpl;

public class GUIController {

    private final GUIService guiService = new GUIServiceImpl();

    public void showHomeWindow(){
        guiService.showHomeWindow();
    }

    public void showEmailsWindow(Person person){
        guiService.showEmailsWindow(person);
    }

    public void showPhonesWindow(Person person){
        guiService.showPhonesWindow(person);
    }

}
