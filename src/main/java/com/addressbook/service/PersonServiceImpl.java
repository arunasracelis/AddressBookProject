package com.addressbook.service;

import com.addressbook.dao.*;
import com.addressbook.entity.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

public class PersonServiceImpl implements AddressBookService<Person> {

    private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);
    private final PersonDAO personDAO = new PersonDAOImpl();

    @Override
    public void add(Session session, Person person) {
        personDAO.addPerson(session, person);
        logger.info("Person " + person.toString() + " added");
    }

    @Override
    public void add(Session session, Integer personId, Person entity) {
    }

    @Override
    public List<Person> list(Session session) {
        return personDAO.listPerson(session);
    }

    @Override
    public List<Person> list(Session session, Integer personId) {
        return null;
    }

    @Override
    public void remove(Session session, Integer personId) {
        personDAO.removePerson(session, personId);
        logger.info("Person id# " + personId + " deleted");
    }

    @Override
    public void remove(Session session, Integer personId, Integer entityId) {
    }

    @Override
    public void update(Session session, Person person) {
        personDAO.updatePerson(session, person);
        logger.info("Updated person " + person.toString() + " saved");
    }


}
