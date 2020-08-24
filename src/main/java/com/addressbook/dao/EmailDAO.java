package com.addressbook.dao;

import com.addressbook.entity.Email;
import org.hibernate.Session;

import java.util.List;

    public interface EmailDAO {
            void addEmail(Session session, Integer personId, Email email);
            List<Email> listEmail(Session session, Integer personId);
            void removeEmail(Session session, Integer personId, Integer emailId);
            void updateEmail(Session session, Email email);
    }

