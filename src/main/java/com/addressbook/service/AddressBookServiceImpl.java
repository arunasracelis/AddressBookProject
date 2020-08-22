package com.addressbook.service;

import com.addressbook.dao.*;
import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AddressBookServiceImpl implements AddressBookService {

    private static final Logger logger = LogManager.getLogger(AddressBookServiceImpl.class);
    private final PersonDAO personDAO = new PersonDAOImpl();
    private final EmailDAO emailDAO = new EmailDAOImpl();
    private final PhoneDAO phoneDAO = new PhoneDAOImpl();

    @Override
    public void addPerson(Person person) {
        personDAO.addPerson(person);
        logger.info("Person " + person.toString() + " added");
    }

    @Override
    public List<Person> listPerson() {
        return personDAO.listPerson();
    }

    @Override
    public void removePerson(Integer id) {
        personDAO.removePerson(id);
        logger.info("Person id# " + id + " deleted");
    }

    @Override
    public void updatePerson(Person person) {
        personDAO.updatePerson(person);
        logger.info("Updated person " + person.toString() + " saved");
    }

    @Override
    public void addEmail(Integer personId, Email email) {
        emailDAO.addEmail(personId, email);
        logger.info("Email " + email.toString() + " added for person id# " + personId);
    }

    @Override
    public List<Email> listEmail(Integer personId) {
        return emailDAO.listEmail(personId);
    }

    @Override
    public void removeEmail(Integer personId, Integer emailId) {
        emailDAO.removeEmail(personId, emailId);
        logger.info("Email id#" + emailId + " deleted for person id# " + personId);
    }

    @Override
    public void updateEmail(Email email) {
        emailDAO.updateEmail(email);
        logger.info("Updated email " + email.toString() + " saved");
    }

    @Override
    public void addPhone(Integer personId, Phone phone) {
        phoneDAO.addPhone(personId, phone);
        logger.info("Phone " + phone.toString() + " added for person id# " + personId);
    }

    @Override
    public List<Phone> listPhone(Integer personId) {
        return phoneDAO.listPhone(personId);
    }

    @Override
    public void removePhone(Integer personId, Integer phoneId) {
        phoneDAO.removePhone(personId, phoneId);
        logger.info("Phone id#" + phoneId + " added for person id# " + personId);
    }

    @Override
    public void updatePhone(Phone phone) {
        phoneDAO.updatePhone(phone);
        logger.info("Updated phone " + phone.toString() + " saved");
    }
}
