package com.addressbook.entity;

import javax.persistence.*;

@Entity
@Table(name="emails")

public class Email {

    @Id
    @GeneratedValue
    @Column(name="email_id")
    private Integer emailId;
    @Column(name="address", nullable=false)
    private String address;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person person;

    public Email(){
    }

    public Email(Integer emailId, String address) {
        this.emailId = emailId;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailId=" + emailId +
                ", address='" + address + '\'' +
                '}';
    }

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
