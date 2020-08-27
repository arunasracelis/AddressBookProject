package test;

import com.addressbook.dao.*;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PhoneEntityTest {

    private final SessionUtils sessionUtils = new SessionUtils();
    private Session session = null;
    private final PersonDAO personDAO = new PersonDAOImpl();
    private final PhoneDAO phoneDAO = new PhoneDAOImpl();
    private Person firstTestPerson, secondTestPerson;
    private Phone firstTestPhone, secondTestPhone;

    @Before
    public void init(){
        session = sessionUtils.openSession();
        createTestPersons();
        createTestPhones();
    }

    private void createTestPersons(){
        firstTestPerson = new Person(1, "Homer", "Simpson");
        secondTestPerson = new Person(2, "Liza", "Simpson");
    }

    private void createTestPhones(){
        firstTestPhone = new Phone(3, "+1234567890");
        secondTestPhone = new Phone(4, "+1234567891");
    }

    @Test
    public void addPhoneTest() {
        personDAO.addPerson(session, firstTestPerson);
        phoneDAO.addPhone(session, firstTestPerson.getPerson_id(), firstTestPhone);
        Phone addedPhone = session.load(Phone.class, 2);
        assertNotNull(addedPhone);
        assertEquals(firstTestPhone.getNumber(), addedPhone.getNumber());
        assertEquals(firstTestPerson.getFirstName(), addedPhone.getPerson().getFirstName());
        assertEquals(firstTestPerson.getLastName(), addedPhone.getPerson().getLastName());
    }

    @Test
    public void listPhoneTest() {
        personDAO.addPerson(session, firstTestPerson);
        personDAO.addPerson(session, secondTestPerson);
        phoneDAO.addPhone(session,firstTestPerson.getPerson_id(), firstTestPhone);
        phoneDAO.addPhone(session,secondTestPerson.getPerson_id(), secondTestPhone);
        List<Phone> phonesList = phoneDAO.listPhone(session, secondTestPerson.getPerson_id());
        assertNotNull(phonesList);
        assertEquals(1, phonesList.size());
        assertEquals(secondTestPhone.getNumber(), phonesList.get(0).getNumber());
        assertEquals(secondTestPerson.getFirstName(), phonesList.get(0).getPerson().getFirstName());
        assertEquals(secondTestPerson.getLastName(), phonesList.get(0).getPerson().getLastName());
    }

    @Test
    public void updatePhoneTest() {
        personDAO.addPerson(session, firstTestPerson);
        phoneDAO.addPhone(session,firstTestPerson.getPerson_id(), firstTestPhone);
        firstTestPhone.setNumber("+18880001234");
        phoneDAO.updatePhone(session, firstTestPhone);
        List<Phone> phoneList = session.createQuery("FROM Phone").list();
        assertNotNull(phoneList);
        assertEquals(1, phoneList.size());
        assertEquals("+18880001234", phoneList.get(0).getNumber());
        assertEquals(firstTestPerson.getFirstName(), phoneList.get(0).getPerson().getFirstName());
        assertEquals(firstTestPerson.getLastName(), phoneList.get(0).getPerson().getLastName());
    }

    @Test
    public void deletePhoneTest() {
        personDAO.addPerson(session, firstTestPerson);
        phoneDAO.addPhone(session,firstTestPerson.getPerson_id(), firstTestPhone);
        phoneDAO.removePhone(session, firstTestPerson.getPerson_id(), firstTestPhone.getPhoneId());
        List<Phone> phoneList = session.createQuery("FROM Phone").list();
        assertEquals(0, phoneList.size());
    }

    @After
    public void shutdown() {
        sessionUtils.shutdown(session);
    }

}
