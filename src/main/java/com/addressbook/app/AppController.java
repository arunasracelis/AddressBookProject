package com.addressbook.app;

import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import com.addressbook.service.AddressBookService;
import com.addressbook.service.AddressBookServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class AppController {

    private AddressBookService addressBookService = new AddressBookServiceImpl();
    private ObservableList<Person> personList= FXCollections.observableArrayList();
    private ObservableList<Email> emailList= FXCollections.observableArrayList();
    private ObservableList<Phone> phoneList= FXCollections.observableArrayList();

    public void addPerson(Person contact){
        addressBookService.addPerson(contact);
    }

    public ObservableList<Person> getPersonList(){
        if(!personList.isEmpty())
            personList.clear();
        personList = FXCollections.observableList((List<Person>) addressBookService.listPerson());
        return personList;
    }

    public ObservableList<Email> getEmailList(Integer personId){
        if(!emailList.isEmpty())
            emailList.clear();
        emailList = FXCollections.observableList((List<Email>) addressBookService.listEmail(personId));
        return emailList;
    }

    public ObservableList<Phone> getPhoneList(Integer personId){
        if(!phoneList.isEmpty())
            phoneList.clear();
        phoneList = FXCollections.observableList((List<Phone>) addressBookService.listPhone(personId));
        return phoneList;
    }

    public void removePerson(Integer id)     {
        addressBookService.removePerson(id);
    }

    public void updatePerson(Person person){
        addressBookService.updatePerson(person);
    }

}
