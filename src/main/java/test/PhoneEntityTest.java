package test;

import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PhoneEntityTest {

    @Rule
    public final SessionFactoryRule sessionFactoryRule = new SessionFactoryRule();

    @Test
    public void addPhoneTest() {
        Session session = sessionFactoryRule.getSession();
        Phone phone = createPhone("+123456789", "Homer", "Simpson");
        session.save(phone);
        Phone addedPhone = session.load(Phone.class, 1);
        assertNotNull(addedPhone);
        assertEquals("+123456789", addedPhone.getNumber());
        assertEquals("Homer", addedPhone.getPerson().getFirstName());
        assertEquals("Simpson", addedPhone.getPerson().getLastName());
    }

    @Test
    public void listPhoneTest() {
        Session session = sessionFactoryRule.getSession();
        Phone firstPhone = createPhone("+123456789", "Homer", "Simpson");
        Phone secondPhone = createPhone("+123456780", "Liza", "Simpson");
        session.save(firstPhone.getPerson());
        session.save(secondPhone.getPerson());
        session.save(firstPhone);
        session.save(secondPhone);
        List<Phone> phones = session.createQuery("FROM Phone").list();
        assertNotNull(phones);
        assertEquals(2, phones.size());
        assertEquals("+123456789", phones.get(0).getNumber());
        assertEquals("+123456780", phones.get(1).getNumber());
    }

    @Test
    public void updatePhoneTest() {
        Session session = sessionFactoryRule.getSession();
        Phone phone = createPhone("+123456789", "Homer", "Simpson");
        session.save(phone.getPerson());
        session.save(phone);
        phone.setNumber("+370123456789");
        session.update(phone);
        List<Phone> phones = session.createQuery("FROM Phone").list();
        assertNotNull(phones);
        assertEquals(1, phones.size());
        assertEquals("+370123456789", phones.get(0).getNumber());
    }

    @Test
    public void deletePhoneTest() {
        Session session = sessionFactoryRule.getSession();
        Phone phone = createPhone("+123456789", "Homer", "Simpson");
        session.save(phone.getPerson());
        session.save(phone);
        session.remove(phone);
        List<Phone> phones = session.createQuery("FROM Phone").list();
        assertEquals(0, phones.size());
    }

    private Phone createPhone(String number, String personFirstName, String personLastName){
        Phone phone = new Phone();
        phone.setNumber(number);
        phone.setPerson(new Person (1, personFirstName, personLastName));
        return phone;
    }

    @After
    public void after() {
        sessionFactoryRule.shutdown();
    }

}
