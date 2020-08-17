package com.addressbook.dao;

import com.addressbook.entity.Person;
import com.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class PersonDAOImpl implements PersonDAO {

    @Override
    public void addPerson(Person person) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(person);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Person> listPerson() {
        List<Person> personList = new ArrayList<>();
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        personList = session.createQuery("FROM Person").list();
        session.getTransaction().commit();
        session.close();
        return personList;
    }

    @Override
    public void removePerson(Integer personId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Person person = (Person) session.load(Person.class , personId);
        session.delete(person);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updatePerson(Person person) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(person);
        session.getTransaction().commit();
        session.close();
    }
}
