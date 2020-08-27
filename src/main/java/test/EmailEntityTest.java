package test;

import com.addressbook.dao.EmailDAO;
import com.addressbook.dao.EmailDAOImpl;
import com.addressbook.dao.PersonDAO;
import com.addressbook.dao.PersonDAOImpl;
import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EmailEntityTest {

    private SessionUtils sessionUtils = new SessionUtils();
    private Session session = null;
    private PersonDAO personDAO = new PersonDAOImpl();
    private EmailDAO emailDAO = new EmailDAOImpl();
    private Person firstTestPerson, secondTestPerson;
    private Email firstTestEmail, secondTestEmail;

    @Before
    public void init(){
        session = sessionUtils.openSession();
        createTestPersons();
        createTestEmails();
    }

    private void createTestPersons(){
        firstTestPerson = new Person(1, "Homer", "Simpson");
        secondTestPerson = new Person(2, "Liza", "Simpson");
    }

    private void createTestEmails(){
        firstTestEmail = new Email(3, "homer@simpson.com");
        secondTestEmail = new Email(4, "liza@simpson.com");
    }

    @Test
    public void addEmailTest() {
        personDAO.addPerson(session, firstTestPerson);
        emailDAO.addEmail(session, firstTestPerson.getPerson_id(), firstTestEmail);
        Email emails = session.load(Email.class, 2);
        assertNotNull(emails);
        assertEquals(firstTestPerson.getFirstName(), emails.getPerson().getFirstName());
        assertEquals(firstTestPerson.getLastName(), emails.getPerson().getLastName());
        assertEquals(firstTestEmail.getAddress(), emails.getAddress());
    }

    @Test
    public void listEmailTest() {
        personDAO.addPerson(session, firstTestPerson);
        personDAO.addPerson(session, secondTestPerson);
        emailDAO.addEmail(session,firstTestPerson.getPerson_id(), firstTestEmail);
        emailDAO.addEmail(session,secondTestPerson.getPerson_id(), secondTestEmail);
        List<Email> emailsList = emailDAO.listEmail(session, secondTestPerson.getPerson_id());
        assertNotNull(emailsList);
        assertEquals(1, emailsList.size());
        assertEquals(secondTestPerson.getFirstName(), emailsList.get(0).getPerson().getFirstName());
        assertEquals(secondTestPerson.getLastName(), emailsList.get(0).getPerson().getLastName());
        assertEquals(secondTestEmail.getAddress(), emailsList.get(0).getAddress());
    }

   @Test
    public void updateEmailTest() {
       personDAO.addPerson(session, firstTestPerson);
       emailDAO.addEmail(session,firstTestPerson.getPerson_id(), firstTestEmail);
       firstTestEmail.setAddress("homer.simpson@mail.com");
       emailDAO.updateEmail(session, firstTestEmail);
       List<Email> emails = session.createQuery("FROM Email").list();
       assertNotNull(emails);
       assertEquals(1, emails.size());
       assertEquals("homer.simpson@mail.com", emails.get(0).getAddress());
    }

    @Test
    public void deleteEmailTest() {
        personDAO.addPerson(session, firstTestPerson);
        emailDAO.addEmail(session,firstTestPerson.getPerson_id(), firstTestEmail);
        emailDAO.removeEmail(session, firstTestPerson.getPerson_id(), firstTestEmail.getEmailId());
        List<Email> emails = session.createQuery("FROM Email").list();
        assertEquals(0, emails.size());
    }

    @After
    public void shutdown() {
        sessionUtils.shutdown(session);
    }

}
