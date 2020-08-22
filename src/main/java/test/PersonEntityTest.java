package test;

import com.addressbook.entity.Person;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

import static org.junit.Assert.*;

public class PersonEntityTest {

    @Rule
    public final SessionFactoryRule sessionFactoryRule = new SessionFactoryRule();

    @Test
    public void addPersonTest() {
        Session session = sessionFactoryRule.getSession();
        Person person = new Person(1, "Homer", "Simpson");
        session.save(person);
        Person addedPerson = session.load(Person.class, 1);
        assertNotNull(addedPerson);
        assertEquals("Homer", addedPerson.getFirstName());
        assertEquals("Simpson", addedPerson.getLastName());
    }

    @Test
    public void listPersonTest() {
        Session session = sessionFactoryRule.getSession();
        Person firstPerson = new Person(1, "Homer", "Simpson");
        Person secondPerson = new Person(1, "Liza", "Simpson");
        session.save(firstPerson);
        session.save(secondPerson);
        List<Person> persons = session.createQuery("FROM Person").list();
        assertNotNull(persons);
        assertEquals(2, persons.size());
        assertEquals("Homer", persons.get(0).getFirstName());
        assertEquals("Simpson", persons.get(0).getLastName());
        assertEquals("Liza", persons.get(1).getFirstName());
        assertEquals("Simpson", persons.get(1).getLastName());
    }

    @Test
    public void updatePersonTest() {
        Session session = sessionFactoryRule.getSession();
        Person firstPerson = new Person(1, "Homer", "Simpson");
        session.save(firstPerson);
        firstPerson.setFirstName("Ned");
        firstPerson.setLastName("Flanders");
        session.update(firstPerson);
        Query query = session.createQuery("FROM Person");
        List<Person> persons = query.getResultList();
        assertNotNull(persons);
        assertEquals(1, persons.size());
        assertEquals("Ned", persons.get(0).getFirstName());
        assertEquals("Flanders", persons.get(0).getLastName());
    }

    @Test
    public void deletePersonTest() {
        Session session = sessionFactoryRule.getSession();
        Person firstPerson = new Person(1, "Homer", "Simpson");
        session.save(firstPerson);
        session.delete(firstPerson);
        Query query = session.createQuery("FROM Person");
        List<Person> persons = query.getResultList();
        assertEquals(0, persons.size());
    }

    @After
    public void after() {
        sessionFactoryRule.shutdown();
    }

}
