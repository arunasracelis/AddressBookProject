package com.addressbook.app;

import com.addressbook.dao.*;
import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //initialize()
        GUIController guiController = new GUIController();
        guiController.main(stage);
    }

    public void initialize() {

        PersonDAO personDAO = new PersonDAOImpl();
        EmailDAO emailDAO = new EmailDAOImpl();
        PhoneDAO phoneDAO = new PhoneDAOImpl();

        Person person1 = new Person(999, "Arunas", "Racelis");
        Person person2 = new Person(999, "Vardenis", "Pavardenis");
        Person person3 = new Person(999, "Tadas", "Blinda");

        Email email1 = new Email("arunas.racelis@mail.com");
        Email email2 = new Email("ar@mail.com");
        Email email3 = new Email("vardenis.pavardenis@mail.com");
        Email email4 = new Email("vp@mail.com");
        Email email5 = new Email("tadas.blinda@mail.com");
        Email email6 = new Email("tb@mail.com");

        Phone phone1 = new Phone("+001");
        Phone phone2 = new Phone("+002");
        Phone phone3 = new Phone("+003");
        Phone phone4 = new Phone("+004");
        Phone phone5 = new Phone("+005");
        Phone phone6 = new Phone("+006");

        personDAO.addPerson(person1);
        personDAO.addPerson(person2);
        personDAO.addPerson(person3);
        emailDAO.addEmail(1, email1);
        emailDAO.addEmail(1, email2);
        emailDAO.addEmail(2, email3);
        emailDAO.addEmail(2, email4);
        emailDAO.addEmail(3, email5);
        emailDAO.addEmail(3, email6);
        phoneDAO.addPhone(1, phone1);
        phoneDAO.addPhone(1, phone2);
        phoneDAO.addPhone(2, phone3);
        phoneDAO.addPhone(2, phone4);
        phoneDAO.addPhone(3, phone5);
        phoneDAO.addPhone(3, phone6);
    }

}
