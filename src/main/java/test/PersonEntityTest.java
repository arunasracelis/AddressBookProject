package test;

import com.addressbook.dao.PersonDAO;
import com.addressbook.dao.PersonDAOImpl;
import com.addressbook.entity.Person;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

import static org.junit.Assert.*;

public class PersonEntityTest {

    private final SessionUtils sessionUtils = new SessionUtils();
    private Session session = null;
    private final PersonDAO personDAO = new PersonDAOImpl();
    private Person firstTestPerson, secondTestPerson;

    @Before
    public void init(){
        session = sessionUtils.openSession();
        createTestPersons();
    }

    @Test
    public void addPersonTest() {
        personDAO.addPerson(session, firstTestPerson);
        List<Person> persons = personDAO.listPerson(session);
        assertNotNull(persons);
        assertEquals(firstTestPerson.getFirstName(), persons.get(0).getFirstName());
        assertEquals(firstTestPerson.getLastName(), persons.get(0).getLastName());
    }

    @Test
    public void listPersonTest() {
        personDAO.addPerson(session, firstTestPerson);
        personDAO.addPerson(session, secondTestPerson);
        List<Person> persons = personDAO.listPerson(session);
        assertNotNull(persons);
        assertEquals(2, persons.size());
        assertEquals(firstTestPerson.getFirstName(), persons.get(0).getFirstName());
        assertEquals(firstTestPerson.getLastName(), persons.get(0).getLastName());
        assertEquals(secondTestPerson.getFirstName(), persons.get(1).getFirstName());
        assertEquals(secondTestPerson.getLastName(), persons.get(1).getLastName());
    }

    @Test
    public void updatePersonTest() {
        personDAO.addPerson(session, firstTestPerson);
        firstTestPerson.setFirstName("Ned");
        firstTestPerson.setLastName("Flanders");
        personDAO.updatePerson(session,firstTestPerson);
        Query query = session.createQuery("FROM Person");
        List<Person> persons = query.getResultList();
        assertNotNull(persons);
        assertEquals(1, persons.size());
        assertEquals("Ned", persons.get(0).getFirstName());
        assertEquals("Flanders", persons.get(0).getLastName());
    }

    @Test
    public void deletePersonTest() {
        personDAO.addPerson(session, firstTestPerson);
        personDAO.removePerson(session,firstTestPerson.getPerson_id());
        Query query = session.createQuery("FROM Person");
        List<Person> persons = query.getResultList();
        assertEquals(0, persons.size());
    }

    private void createTestPersons(){
        firstTestPerson = new Person(1, "Homer", "Simpson");
        secondTestPerson = new Person(2, "Liza", "Simpson");
    }

    @After
    public void shutdown() {
        sessionUtils.shutdown(session);
    }

}
