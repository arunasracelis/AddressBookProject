package com.addressbook.dao;

import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.gui.AlertBox;
import com.addressbook.utils.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Query;
import java.util.List;

import static com.addressbook.utils.ExceptionUtils.DEFAULT_ERROR_MESSAGE;

public class EmailDAOImpl implements EmailDAO {

    private static final Logger logger = LogManager.getLogger(EmailDAOImpl.class);

    @Override
    public void addEmail(Session session, Integer personId, Email email) {
        try {
            Transaction transaction = session.beginTransaction();
            Person person = session.load(Person.class, personId);
            person.getEmails().add(email);
            email.setPerson(person);
            session.save(person);
            session.save(email);
            transaction.commit();
        } catch (Exception e) {
            logger.error("ERROR! addEmail failed: " + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", DEFAULT_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Email> listEmail(Session session, Integer personId) {
        try {
            List<Email> emailList;
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Email WHERE person_id = :personId");
            query.setParameter("personId", personId);
            emailList = query.getResultList();
            transaction.commit();
            return emailList;
        } catch (Exception e) {
            logger.error("ERROR! listEmail failed: " +  ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", DEFAULT_ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public void removeEmail(Session session, Integer personId, Integer emailId) {
        try {
            Transaction transaction = session.beginTransaction();
            Person person = session.load(Person.class , personId);
            Email email = session.load(Email.class , emailId);
            person.getEmails().remove(email);
            email.setPerson(null);
            session.save(person);
            session.delete(email);
            transaction.commit();
        } catch (Exception e) {
            logger.error("ERROR! removeEmail failed: " +  ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", DEFAULT_ERROR_MESSAGE);
        }
    }

    @Override
    public void updateEmail(Session session, Email email) {
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE Email SET address = :emailAddress WHERE email_id = :emailId");
            query.setParameter("emailAddress", email.getAddress());
            query.setParameter("emailId", email.getEmailId());
            int result = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            logger.error("ERROR! updateEmail failed: "  + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", DEFAULT_ERROR_MESSAGE);
        }
    }
}