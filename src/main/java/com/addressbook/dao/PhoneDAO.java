package com.addressbook.dao;

import com.addressbook.entity.Phone;
import org.hibernate.Session;

import java.util.List;

public interface PhoneDAO {
    void addPhone(Session session,  Integer personId, Phone phone);
    List<Phone> listPhone(Session session, Integer personId);
    void removePhone(Session session, Integer personId, Integer phoneId);
    void updatePhone(Session session, Phone phone);
}