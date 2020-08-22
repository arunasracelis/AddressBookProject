package com.addressbook.service;

import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;

import java.util.List;

public interface AddressBookService {
    void addPerson(Person person);
    List<Person> listPerson();
    void removePerson(Integer id);
    void updatePerson(Person person);

    void addEmail(Integer personId, Email email);
    List<Email> listEmail(Integer personId);
    void removeEmail(Integer personId, Integer emailId);
    void updateEmail(Email email);

    void addPhone(Integer personId, Phone phone);
    List<Phone> listPhone(Integer personId);
    void removePhone(Integer personId, Integer phoneId);
    void updatePhone(Phone phone);
}