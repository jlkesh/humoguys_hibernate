package dev.jlkesh;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Book book = Book.builder()
                .title("Spring Boot in action")
                .author("Humo Guys")
                .build();
        session.persist(book);
        transaction.commit();
    }

}