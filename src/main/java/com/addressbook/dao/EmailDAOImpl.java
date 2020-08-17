package com.addressbook.dao;

import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EmailDAOImpl implements EmailDAO {

    @Override
    public void addEmail(Integer personId, Email email) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Person person = (Person) session.load(Person.class, personId);
        person.getEmails().add(email);
        email.setPerson(person);
        session.save(person);
        session.save(email);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Email> listEmail(Integer personId) {
        List<Email> emailList = new ArrayList<>();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Email WHERE person_id = :personId");
        query.setParameter("personId", personId);
        emailList = query.getResultList();
        transaction.commit();
        session.close();
        return emailList;
    }

    @Override
    public void removeEmail(Integer personId, Integer emailId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Person person = (Person) session.load(Person.class , personId);
        Email email = (Email) session.load(Email.class , emailId);
        person.getEmails().remove(email);
        email.setPerson(null);
        session.save(person);
        session.delete(email);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateEmail(Email email) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(email);
        session.getTransaction().commit();
        session.close();
    }
}