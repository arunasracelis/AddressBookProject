package com.addressbook.dao;

import com.addressbook.entity.Person;
import com.addressbook.gui.AlertBox;
import com.addressbook.utils.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PersonDAOImpl implements PersonDAO {

    private static final Logger logger = LogManager.getLogger(PersonDAOImpl.class);

    @Override
    public void addPerson(Session session, Person person) {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
        } catch (Exception e) {
            logger.error("ERROR! addPerson failed: "  + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
        }
    }

    @Override
    public List<Person> listPerson(Session session) {
        try {
            List<Person> personList;
            Transaction transaction = session.beginTransaction();
            personList = session.createQuery("FROM Person").list();
            transaction.commit();
            return personList;
        } catch (Exception e) {
            logger.error("ERROR! listPerson failed: "  + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
            return null;
        }
    }

    @Override
    public void removePerson(Session session, Integer personId) {
        try {
            Transaction transaction = session.beginTransaction();
            Person person = session.load(Person.class , personId);
            session.delete(person);
            transaction.commit();
        } catch (Exception e) {
            logger.error("ERROR! removePerson failed: "  + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
        }
    }

    @Override
    public void updatePerson(Session session, Person person) {
        try {
            Transaction transaction = session.beginTransaction();
            session.update(person);
            transaction.commit();
        } catch (Exception e) {
            logger.error("ERROR! updatePerson failed:"  + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
        }
    }
}
