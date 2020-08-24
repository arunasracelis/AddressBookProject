package com.addressbook.dao;

import com.addressbook.entity.Person;
import org.hibernate.Session;

import java.util.List;

    public interface PersonDAO {
        void addPerson(Session session, Person person);
        List<Person> listPerson(Session session);
        void removePerson(Session session, Integer personId);
        void updatePerson(Session session, Person person);
    }

