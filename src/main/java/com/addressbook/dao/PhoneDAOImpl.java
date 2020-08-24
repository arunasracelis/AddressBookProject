package com.addressbook.dao;

import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import com.addressbook.gui.AlertBox;
import com.addressbook.utils.ExceptionUtils;
import com.addressbook.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Query;
import java.util.List;

public class PhoneDAOImpl implements PhoneDAO {

    private static final Logger logger = LogManager.getLogger(HibernateUtils.class);

    @Override
    public void addPhone(Session session, Integer personId, Phone phone) {
        try {
            Transaction transaction = session.beginTransaction();
            Person person = session.load(Person.class , personId);
            person.getPhones().add(phone);
            phone.setPerson(person);
            session.save(person);
            session.save(phone);
            transaction.commit();
        } catch (Exception e) {
            logger.error("ERROR! addPhone failed: "  + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
        }
    }

    @Override
    public List<Phone> listPhone(Session session, Integer personId) {
        try {
            List<Phone> phoneList;
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Phone WHERE person_id = :personId");
            query.setParameter("personId", personId);
            phoneList = query.getResultList();
            transaction.commit();
            return phoneList;
        } catch (Exception e) {
            logger.error("ERROR! listPhone failed: "  + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
            return null;
        }
    }

    @Override
    public void removePhone(Session session, Integer personId, Integer phoneId) {
        try {
            Transaction transaction = session.beginTransaction();
            Person person = session.load(Person.class , personId);
            Phone phone = session.load(Phone.class , phoneId);
            person.getPhones().remove(phone);
            phone.setPerson(null);
            session.save(person);
            session.delete(phone);
            transaction.commit();
        } catch (Exception e) {
            logger.error("ERROR! removePhone failed: "  + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
        }
    }

    @Override
    public void updatePhone(Session session, Phone phone) {
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE Phone SET number = :phoneNumber WHERE phone_id = :phoneId");
            query.setParameter("phoneNumber", phone.getNumber());
            query.setParameter("phoneId", phone.getPhoneId());
            int result = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            logger.error("ERROR! updatePhone failed: "  + ExceptionUtils.findRootCause(e));
            AlertBox.show("Error!", "Action failed...");
        }
    }
}
