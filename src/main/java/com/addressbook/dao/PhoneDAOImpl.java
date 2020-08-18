package com.addressbook.dao;

import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import com.addressbook.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class PhoneDAOImpl implements PhoneDAO {

    @Override
    public void addPhone(Integer personId, Phone phone) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Person person = session.load(Person.class , personId);
            person.getPhones().add(phone);
            phone.setPerson(person);
            session.save(person);
            session.save(phone);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Phone> listPhone(Integer personId) {
        try {
            List<Phone> phoneList;
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Phone WHERE person_id = :personId");
            query.setParameter("personId", personId);
            phoneList = query.getResultList();
            transaction.commit();
            session.close();
            return phoneList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removePhone(Integer personId, Integer phoneId) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Person person = session.load(Person.class , personId);
            Phone phone = session.load(Phone.class , phoneId);
            person.getPhones().remove(phone);
            phone.setPerson(null);
            session.save(person);
            session.delete(phone);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePhone(Phone phone) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE Phone SET number = :phoneNumber WHERE phone_id = :phoneId");
            query.setParameter("phoneNumber", phone.getNumber());
            query.setParameter("phoneId", phone.getPhoneId());
            int result = query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
