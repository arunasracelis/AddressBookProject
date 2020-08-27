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

    private ObservableList<Person> personList= FXCollections.observableArrayList();
    private ObservableList<Email> emailList= FXCollections.observableArrayList();
    private ObservableList<Phone> phoneList= FXCollections.observableArrayList();

    public void addPerson(Person contact){
        Session session = HibernateUtils.getSessionFactory().openSession();
        personService.add(session, contact);
        session.close();
    }

    public void addEmail(Integer personId, Email email){
        Session session = HibernateUtils.getSessionFactory().openSession();
        emailService.add(session, personId, email);
        session.close();
    }

    public void addPhone(Integer personId, Phone phone){
        Session session = HibernateUtils.getSessionFactory().openSession();
        phoneService.add(session, personId, phone);
        session.close();
    }

    public ObservableList<Person> getPersonList(){
        Session session = HibernateUtils.getSessionFactory().openSession();
        if(!personList.isEmpty())
            personList.clear();
        personList = FXCollections.observableList(personService.list(session));
        session.close();
        return personList;
    }

    public ObservableList<Email> getEmailList(Integer personId){
        Session session = HibernateUtils.getSessionFactory().openSession();
        if(!emailList.isEmpty())
            emailList.clear();
        emailList = FXCollections.observableList(emailService.list(session, personId));
        session.close();
        return emailList;
    }

    public ObservableList<Phone> getPhoneList(Integer personId){
        Session session = HibernateUtils.getSessionFactory().openSession();
        if(!phoneList.isEmpty())
            phoneList.clear();
        phoneList = FXCollections.observableList(phoneService.list(session, personId));
        session.close();
        return phoneList;
    }

    public void removePerson(Integer personId)     {
        Session session = HibernateUtils.getSessionFactory().openSession();
        personService.remove(session, personId);
        session.close();
    }

    public void removeEmail(Integer personId, Integer emailId)     {
        Session session = HibernateUtils.getSessionFactory().openSession();
        emailService.remove(session, personId, emailId);
        session.close();
    }

    public void removePhone(Integer personId, Integer phoneId)     {
        Session session = HibernateUtils.getSessionFactory().openSession();
        phoneService.remove(session, personId, phoneId);
        session.close();
    }

    public void updatePerson(Person person){
        Session session = HibernateUtils.getSessionFactory().openSession();
        personService.update(session, person);
        session.close();
    }

    public void updateEmail(Email email){
        Session session = HibernateUtils.getSessionFactory().openSession();
        emailService.update(session, email);
        session.close();
    }

    public void updatePhone(Phone phone){
        Session session = HibernateUtils.getSessionFactory().openSession();
        phoneService.update(session, phone);
        session.close();
    }

}
