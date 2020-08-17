package com.addressbook.entity;

import javax.persistence.*;

@Entity
@Table(name="phones")

public class Phone {

    @Id
    @GeneratedValue
    @Column(name="phone_id", nullable=false)
    private Integer id;
    @Column(name="number", nullable=false)
    private String number;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person person;

    public Phone() {
    }

    public Phone(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
