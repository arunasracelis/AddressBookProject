package com.addressbook.service;

import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;

import java.util.List;

public interface AddressBookService {
    public void addPerson(Person person);
    public List<Person> listPerson();
    public void removePerson(Integer id);
    public void updatePerson(Person person);

    public void addEmail(Integer personId, Email email);
    public List<Email> listEmail(Integer personId);
    public void removeEmail(Integer personId, Integer emailId);
    public void updateEmail(Email email);

    public void addPhone(Integer personId, Phone phone);
    public List<Phone> listPhone(Integer personId);
    public void removePhone(Integer personId, Integer phoneId);
    public void updatePhone(Phone phone);
}