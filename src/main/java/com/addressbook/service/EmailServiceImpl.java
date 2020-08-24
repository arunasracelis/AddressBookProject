package com.addressbook.service;

import com.addressbook.dao.EmailDAO;
import com.addressbook.dao.EmailDAOImpl;
import com.addressbook.entity.Email;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

public class EmailServiceImpl implements AddressBookService<Email> {

    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
    private final EmailDAO emailDAO = new EmailDAOImpl();

    @Override
    public void add(Session session, Email email) {
    }

    @Override
    public void add(Session session, Integer personId, Email email) {
        emailDAO.addEmail(session, personId, email);
        logger.info("Email " + email.toString() + " added for person id# " + personId);
    }

    @Override
    public List<Email> list(Session session) {
        return null;
    }

    @Override
    public List<Email> list(Session session, Integer personId) {
        return emailDAO.listEmail(session, personId);
    }

    @Override
    public void remove(Session session, Integer personId) {
    }

    @Override
    public void remove(Session session, Integer personId, Integer emailId) {
        emailDAO.removeEmail(session, personId, emailId);
        logger.info("Email id#" + emailId + " deleted for person id# " + personId);
    }

    @Override
    public void update(Session session, Email email) {
        emailDAO.updateEmail(session, email);
        logger.info("Updated email " + email.toString() + " saved");
    }
}
