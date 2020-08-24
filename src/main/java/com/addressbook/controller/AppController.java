package com.addressbook.controller;

import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import com.addressbook.service.*;
import com.addressbook.utils.HibernateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

public class AppController {

    private final AddressBookService<Person> personService = new PersonServiceImpl();
    private final AddressBookService<Email> emailService = new EmailServiceImpl();
    private final AddressBookService<Phone> phoneService = new PhoneServiceImpl();
    public static Session session = HibernateUtils.getSessionFactory().openSession();

    private ObservableList<Person> personList= FXCollections.observableArrayList();
    private ObservableList<Email> emailList= FXCollections.observableArrayList();
    private ObservableList<Phone> phoneList= FXCollections.observableArrayList();

    public void addPerson(Person contact){
        personService.add(session, contact);
    }

    public void addEmail(Integer personId, Email email){
        emailService.add(session, personId, email);
    }

    public void addPhone(Integer personId, Phone phone){
        phoneService.add(session, personId, phone);
    }

    public ObservableList<Person> getPersonList(){
        if(!personList.isEmpty())
            personList.clear();
        personList = FXCollections.observableList(personService.list(session));
        return personList;
    }

    public ObservableList<Email> getEmailList(Integer personId){
        if(!emailList.isEmpty())
            emailList.clear();
        emailList = FXCollections.observableList(emailService.list(session, personId));
        return emailList;
    }

    public ObservableList<Phone> getPhoneList(Integer personId){
        if(!phoneList.isEmpty())
            phoneList.clear();
        phoneList = FXCollections.observableList(phoneService.list(session, personId));
        return phoneList;
    }

    public void removePerson(Integer personId)     {
        personService.remove(session, personId);
    }

    public void removeEmail(Integer personId, Integer emailId)     {
        emailService.remove(session, personId, emailId);
    }

    public void removePhone(Integer personId, Integer phoneId)     {
        phoneService.remove(session, personId, phoneId);
    }

    public void updatePerson(Person person){
        personService.update(session, person);
    }

    public void updateEmail(Email email){
        emailService.update(session, email);
    }

    public void updatePhone(Phone phone){
        phoneService.update(session, phone);
    }

}
