package com.addressbook.dao;

import com.addressbook.entity.Person;

import java.util.List;

    public interface PersonDAO {
        void addPerson(Person person);
        List<Person> listPerson();
        void removePerson(Integer personId);
        void updatePerson(Person person);
    }

