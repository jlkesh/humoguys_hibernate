package dev.jlkesh;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
    private static SessionFactory sessionFactory = load();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    ;

    private static SessionFactory load() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }
}
