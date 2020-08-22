package com.addressbook.dao;

import com.addressbook.entity.Email;

import java.util.List;

    public interface EmailDAO {
            void addEmail(Integer personId, Email email);
            List<Email> listEmail(Integer personId);
            void removeEmail(Integer personId, Integer emailId);
            void updateEmail(Email email);
    }

