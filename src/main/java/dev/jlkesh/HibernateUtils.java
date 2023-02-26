package dev.jlkesh;

import dev.jlkesh.Author;
import dev.jlkesh.Book;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtils {
    private static SessionFactory sessionFactory = load();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static SessionFactory load() {
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "org.postgresql.Driver");
        settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/humoguys?currentSchema=hibernate");
        settings.put(Environment.USER, "postgres");
        settings.put(Environment.PASS, "123");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.FORMAT_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "update");
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(Author.class);
        configuration.addAnnotatedClass(Book.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

     void hh() {

    }
}
