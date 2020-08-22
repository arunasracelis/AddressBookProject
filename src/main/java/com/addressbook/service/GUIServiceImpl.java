package com.addressbook.service;

import com.addressbook.entity.Person;
import com.addressbook.gui.EmailsWindow;
import com.addressbook.gui.HomeWindow;
import com.addressbook.gui.PhonesWindow;

public class GUIServiceImpl implements GUIService {

    @Override
    public void showHomeWindow() {
        HomeWindow.show();
    }

    @Override
    public void showEmailsWindow(Person person) {
        EmailsWindow.show(person);
    }

    @Override
    public void showPhonesWindow(Person person) {
        PhonesWindow.show(person);
    }
}
