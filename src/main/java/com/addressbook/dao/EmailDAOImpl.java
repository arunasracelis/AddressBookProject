package com.addressbook.dao;

import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.gui.AlertBox;
import com.addressbook.utils.ExceptionUtils;
import com.addressbook.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Query;
import java.util.List;

public class EmailDAOImpl implements EmailDAO {

    private static final Logger logger = LogManager.getLogger(EmailDAOImpl.class);

    @Override
    public void addEmail(Integer personId, Email email) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Person person = session.load(Person.class, personId);
            person.getEmails().add(email);
            email.setPerson(person);
            session.save(person);
            session.save(email);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            logger.error("ERROR! addEmail failed: " + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
        }
    }

    @Override
    public List<Email> listEmail(Integer personId) {
        try {
            List<Email> emailList;
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Email WHERE person_id = :personId");
            query.setParameter("personId", personId);
            emailList = query.getResultList();
            transaction.commit();
            session.close();
            return emailList;
        } catch (Exception e) {
            logger.error("ERROR! listEmail failed: " +  ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
            return null;
        }
    }

    @Override
    public void removeEmail(Integer personId, Integer emailId) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Person person = session.load(Person.class , personId);
            Email email = session.load(Email.class , emailId);
            person.getEmails().remove(email);
            email.setPerson(null);
            session.save(person);
            session.delete(email);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            logger.error("ERROR! removeEmail failed: " +  ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
        }
    }

    @Override
    public void updateEmail(Email email) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE Email SET address = :emailAddress WHERE email_id = :emailId");
            query.setParameter("emailAddress", email.getAddress());
            query.setParameter("emailId", email.getEmailId());
            int result = query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            logger.error("ERROR! updateEmail failed: "  + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
        }
    }
}