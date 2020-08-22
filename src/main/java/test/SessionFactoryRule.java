package test;

import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

// https://dzone.com/articles/testing-databases-junit-and

public class SessionFactoryRule implements MethodRule {

    private SessionFactory sessionFactory;
    private Transaction transaction;
    private Session session;

    @Override
    public Statement apply(final Statement statement, FrameworkMethod method, Object test) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                sessionFactory = createSessionFactory();
                createSession();
                beginTransaction();
                try {
                    statement.evaluate();
                } finally {
                    shutdown();
                }
            }
        };
    }

    public void shutdown() {
        try {
            try {
                try {
                    transaction.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                session.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            sessionFactory.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(Email.class);
        configuration.addAnnotatedClass(Phone.class);
        configuration.setProperty("hibernate.dialect","org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class","org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:myTestDB");
        configuration.setProperty("hibernate.hbm2ddl.auto","create");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }

    public Session createSession() {
        session = sessionFactory.openSession();
        return session;
    }
    public void commit() {
        transaction.commit();
    }
    public void beginTransaction() {
        transaction = session.beginTransaction();
    }
    public Session getSession() {
        return session;
    }

}