package com.addressbook.dao;

import com.addressbook.entity.Person;

import java.util.List;

    public interface PersonDAO {
        public void addPerson(Person person);
        public List<Person> listPerson();
        public void removePerson(Integer personId);
        public void updatePerson(Person person);
    }

