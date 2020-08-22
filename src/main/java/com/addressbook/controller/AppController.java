package com.addressbook.controller;

import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import com.addressbook.service.AddressBookService;
import com.addressbook.service.AddressBookServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppController {

    private final AddressBookService addressBookService = new AddressBookServiceImpl();
    private ObservableList<Person> personList= FXCollections.observableArrayList();
    private ObservableList<Email> emailList= FXCollections.observableArrayList();
    private ObservableList<Phone> phoneList= FXCollections.observableArrayList();

    public void addPerson(Person contact){
        addressBookService.addPerson(contact);
    }

    public void addEmail(Integer personId, Email email){
        addressBookService.addEmail(personId, email);
    }

    public void addPhone(Integer personId, Phone phone){
        addressBookService.addPhone(personId, phone);
    }

    public ObservableList<Person> getPersonList(){
        if(!personList.isEmpty())
            personList.clear();
        personList = FXCollections.observableList(addressBookService.listPerson());
        return personList;
    }

    public ObservableList<Email> getEmailList(Integer personId){
        if(!emailList.isEmpty())
            emailList.clear();
        emailList = FXCollections.observableList(addressBookService.listEmail(personId));
        return emailList;
    }

    public ObservableList<Phone> getPhoneList(Integer personId){
        if(!phoneList.isEmpty())
            phoneList.clear();
        phoneList = FXCollections.observableList(addressBookService.listPhone(personId));
        return phoneList;
    }

    public void removePerson(Integer id)     {
        addressBookService.removePerson(id);
    }

    public void removeEmail(Integer personId, Integer emailId)     {
        addressBookService.removeEmail(personId, emailId);
    }

    public void removePhone(Integer personId, Integer phoneId)     {
        addressBookService.removePhone(personId, phoneId);
    }

    public void updatePerson(Person person){
        addressBookService.updatePerson(person);
    }

    public void updateEmail(Email email){
        addressBookService.updateEmail(email);
    }

    public void updatePhone(Phone phone){
        addressBookService.updatePhone(phone);
    }

}
