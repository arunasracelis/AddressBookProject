package com.addressbook.dao;

import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import com.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class PhoneDAOImpl implements PhoneDAO {

    @Override
    public void addPhone(Integer personId, Phone phone) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Person person = (Person) session.load(Person.class , personId);
        person.getPhones().add(phone);
        phone.setPerson(person);
        session.save(person);
        session.save(phone);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Phone> listPhone(Integer personId) {
        List<Phone> phoneList = new ArrayList<>();
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        phoneList = session.createQuery("FROM Phone WHERE personId = " + personId).list();
        session.getTransaction().commit();
        session.close();
        return phoneList;
    }

    @Override
    public void removePhone(Integer personId, Integer phoneId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Person person = (Person) session.load(Person.class , personId);
        Phone phone = (Phone) session.load(Phone.class , phoneId);
        person.getPhones().remove(phone);
        phone.setPerson(null);
        session.save(person);
        session.delete(phone);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updatePhone(Phone phone) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(phone);
        session.getTransaction().commit();
        session.close();
    }
}
