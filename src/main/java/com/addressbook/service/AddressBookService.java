package com.addressbook.service;

import org.hibernate.Session;

import java.util.List;

public interface AddressBookService<T> {

    void add(Session session, T entity);
    void add(Session session, Integer personId, T entity);
    List<T> list(Session session);
    List<T> list(Session session, Integer personId);
    void remove(Session session, Integer personId);
    void remove(Session session, Integer personId, Integer entityId);
    void update(Session session, T entity);

}
