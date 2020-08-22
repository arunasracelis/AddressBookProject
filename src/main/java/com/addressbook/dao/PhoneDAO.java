package com.addressbook.dao;

import com.addressbook.entity.Phone;

import java.util.List;

public interface PhoneDAO {
    void addPhone(Integer personId, Phone phone);
    List<Phone> listPhone(Integer personId);
    void removePhone(Integer personId, Integer phoneId);
    void updatePhone(Phone phone);
}