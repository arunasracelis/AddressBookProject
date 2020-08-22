package com.addressbook.service;

import com.addressbook.entity.Person;

public interface GUIService {

    void showHomeWindow();
    void showEmailsWindow(Person person);
    void showPhonesWindow(Person person);

}
