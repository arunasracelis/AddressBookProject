package com.addressbook.dao;

import com.addressbook.entity.Email;

import java.util.List;

    public interface EmailDAO {
            public void addEmail(Integer personId, Email email);
            public List<Email> listEmail(Integer personId);
            public void removeEmail(Integer personId, Integer emailId);
            public void updateEmail(Email email);
    }

