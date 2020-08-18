package com.addressbook.entity;

import javax.persistence.*;

@Entity
@Table(name="phones")

public class Phone {

    @Id
    @GeneratedValue
    @Column(name="phone_id", nullable=false)
    private Integer phoneId;
    @Column(name="number", nullable=false)
    private String number;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person person;

    public Phone() {
    }

    public Phone(Integer phoneId, String number) {
        this.phoneId = phoneId;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "phoneId=" + phoneId +
                ", number='" + number + '\'' +
                '}';
    }

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
