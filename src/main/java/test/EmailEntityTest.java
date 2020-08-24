package test;

import com.addressbook.dao.EmailDAO;
import com.addressbook.dao.EmailDAOImpl;
import com.addressbook.dao.PersonDAOImpl;
import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EmailEntityTest {

    @Rule
    public final SessionFactoryRule sessionFactoryRule = new SessionFactoryRule();

    @Test
    public void addEmailTest() {
        Session session = sessionFactoryRule.getSession();
        PersonDAOImpl personDAO = new PersonDAOImpl();
        EmailDAO emailDAO = new EmailDAOImpl();
        Person person = new Person(1, "Homer", "Simpson");
        Email email = new Email (1, "homer@simpson.com");
        email.setPerson(person);
        personDAO.addPerson(session, person);
        emailDAO.addEmail(session, person.getPerson_id(),email);
        Email addedEmail = session.load(Email.class, 2);
        assertNotNull(addedEmail);
        assertEquals("homer@simpson.com", addedEmail.getAddress());
        assertEquals("Homer", addedEmail.getPerson().getFirstName());
        assertEquals("Simpson", addedEmail.getPerson().getLastName());
    }

    @Test
    public void listEmailTest() {
        Session session = sessionFactoryRule.getSession();
        Email firstEmail = createEmail("homer@simpson.com", "Homer", "Simpson");
        Email secondEmail = createEmail("liza@simpson.com", "Liza", "Simpson");
        session.save(firstEmail.getPerson());
        session.save(secondEmail.getPerson());
        session.save(firstEmail);
        session.save(secondEmail);
        List<Email> emails = session.createQuery("FROM Email").list();
        assertNotNull(emails);
        assertEquals(2, emails.size());
        assertEquals("homer@simpson.com", emails.get(0).getAddress());
        assertEquals("liza@simpson.com", emails.get(1).getAddress());
    }

    @Test
    public void updateEmailTest() {
        Session session = sessionFactoryRule.getSession();
        Email email = createEmail("homer@simpson.com", "Homer", "Simpson");
        session.save(email.getPerson());
        session.save(email);
        email.setAddress("homer.simpson@mail.com");
        session.update(email);
        List<Email> emails = session.createQuery("FROM Email").list();
        assertNotNull(emails);
        assertEquals(1, emails.size());
        assertEquals("homer.simpson@mail.com", emails.get(0).getAddress());
    }

    @Test
    public void deleteEmailTest() {
        Session session = sessionFactoryRule.getSession();
        Email email = createEmail("homer@simpson.com", "Homer", "Simpson");
        session.save(email.getPerson());
        session.save(email);
        session.remove(email);
        List<Email> emails = session.createQuery("FROM Email").list();
        assertEquals(0, emails.size());
    }

    private Email createEmail(String address, String personFirstName, String personLastName){
        Email email = new Email();
        email.setAddress(address);
        email.setPerson(new Person (1, personFirstName, personLastName));
        return email;
    }

    @After
    public void after() {
        sessionFactoryRule.shutdown();
    }

}
