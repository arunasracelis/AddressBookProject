package test;

import com.addressbook.entity.Email;
import com.addressbook.entity.Person;
import com.addressbook.entity.Phone;
import com.addressbook.utils.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionUtils {

    private static final Logger logger = LogManager.getLogger(SessionUtils.class);

    public Session openSession() {
        try {
            Session session = createSessionFactory().openSession();
            return session;
        } catch (Exception e) {
            logger.error("ERROR! Failed to open session: " +  ExceptionUtils.findRootCause(e));
            return null;
        }
    }

    private SessionFactory createSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Person.class);
            configuration.addAnnotatedClass(Email.class);
            configuration.addAnnotatedClass(Phone.class);
            configuration.setProperty("hibernate.dialect","org.hibernate.dialect.H2Dialect");
            configuration.setProperty("hibernate.connection.driver_class","org.h2.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:myTestDB");
            configuration.setProperty("hibernate.hbm2ddl.auto","create");
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            logger.info("SessionFactory created");
            return sessionFactory;
        } catch (Exception e) {
            logger.error("ERROR! Failed to crate session factory: " +  ExceptionUtils.findRootCause(e));
            return null;
        }
    }

    public void shutdown(Session session) {
        try {
            session.close();
        } catch (Exception e) {
            logger.error("ERROR! Failed to close session: " +  ExceptionUtils.findRootCause(e));
        }
    }

}
