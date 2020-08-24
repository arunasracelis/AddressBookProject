package com.addressbook.service;

import com.addressbook.dao.PhoneDAO;
import com.addressbook.dao.PhoneDAOImpl;
import com.addressbook.entity.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

public class PhoneServiceImpl implements AddressBookService<Phone> {

    private static final Logger logger = LogManager.getLogger(PhoneServiceImpl.class);
    private final PhoneDAO phoneDAO = new PhoneDAOImpl();

    @Override
    public void add(Session session, Phone phone) {
    }

    @Override
    public void add(Session session, Integer personId, Phone phone) {
        phoneDAO.addPhone(session, personId, phone);
        logger.info("Phone " + phone.toString() + " added for person id# " + personId);
    }

    @Override
    public List<Phone> list(Session session) {
        return null;
    }

    @Override
    public List<Phone> list(Session session, Integer personId) {
        return phoneDAO.listPhone(session, personId);
    }

    @Override
    public void remove(Session session, Integer personId) {
    }

    @Override
    public void remove(Session session, Integer personId, Integer phoneId) {
        phoneDAO.removePhone(session, personId, phoneId);
        logger.info("Phone id#" + phoneId + " added for person id# " + personId);
    }

    @Override
    public void update(Session session, Phone phone) {
        phoneDAO.updatePhone(session, phone);
        logger.info("Updated phone " + phone.toString() + " saved");
    }
}
