package com.addressbook.dao;

import com.addressbook.entity.Phone;

import java.util.List;

public interface PhoneDAO {
    public void addPhone(Integer personId, Phone phone);
    public List<Phone> listPhone(Integer personId);
    public void removePhone(Integer personId, Integer phoneId);
    public void updatePhone(Phone phone);
}