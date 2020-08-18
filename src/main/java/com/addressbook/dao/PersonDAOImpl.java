package com.addressbook.dao;

import com.addressbook.entity.Person;
import com.addressbook.gui.AlertBox;
import com.addressbook.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PersonDAOImpl implements PersonDAO {

    @Override
    public void addPerson(Person person) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            AlertBox.show("Error!", "Action failed...");
            e.printStackTrace();
        }
    }

    @Override
    public List<Person> listPerson() {
        try {
            List<Person> personList;
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            personList = session.createQuery("FROM Person").list();
            transaction.commit();
            session.close();
            return personList;
        } catch (Exception e) {
            AlertBox.show("Error!", "Action failed...");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removePerson(Integer personId) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Person person = session.load(Person.class , personId);
            session.delete(person);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            AlertBox.show("Error!", "Action failed...");
            e.printStackTrace();
        }
    }

    @Override
    public void updatePerson(Person person) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(person);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            AlertBox.show("Error!", "Action failed...");
            e.printStackTrace();
        }
    }
}
