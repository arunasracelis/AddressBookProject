package com.addressbook.service;

import com.addressbook.dao.*;
import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;

import java.util.List;

public class AddressBookServiceImpl implements AddressBookService {

    private PersonDAO personDAO = new PersonDAOImpl();
    private EmailDAO emailDAO = new EmailDAOImpl();
    private PhoneDAO phoneDAO = new PhoneDAOImpl();

    @Override
    public void addPerson(Person person) {
        personDAO.addPerson(person);
    }

    @Override
    public List<Person> listPerson() {
        return personDAO.listPerson();
    }

    @Override
    public void removePerson(Integer id) {
        personDAO.removePerson(id);
    }

    @Override
    public void updatePerson(Person person) {
        personDAO.updatePerson(person);
    }

    @Override
    public void addEmail(Integer personId, Email email) {
        emailDAO.addEmail(personId, email);
    }

    @Override
    public List<Email> listEmail(Integer personId) {
        return emailDAO.listEmail(personId);
    }

    @Override
    public void removeEmail(Integer personId, Integer emailId) {
        emailDAO.removeEmail(personId, emailId);
    }

    @Override
    public void updateEmail(Email email) {
        emailDAO.updateEmail(email);
    }

    @Override
    public void addPhone(Integer personId, Phone phone) {
        phoneDAO.addPhone(personId, phone);
    }

    @Override
    public List<Phone> listPhone(Integer personId) {
        return phoneDAO.listPhone(personId);
    }

    @Override
    public void removePhone(Integer personId, Integer phoneId) {
        phoneDAO.removePhone(personId, phoneId);
    }

    @Override
    public void updatePhone(Phone phone) {
        phoneDAO.updatePhone(phone);
    }
}
