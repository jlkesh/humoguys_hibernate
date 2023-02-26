package dev.jlkesh;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class BookService extends CRUDService<Book, String> {
    @Override
    public Book save(Book book) {
        Session session = getSession();
        session.getTransaction().begin();
        session.persist(book);
        session.getTransaction().commit();
        return book;
    }

    @Override
    public Book get(String id) {
        Session session = getSession();
        session.getTransaction().begin();
        Book book = session.find(Book.class, id);
        session.getTransaction().commit();
        return book;
    }

    @Override
    public boolean update(Book book) {
        Session session = getSession();
        session.getTransaction().begin();
        session.merge(book);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(Book book) {
        Session session = getSession();
        session.getTransaction().begin();
        session.remove(book);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        Session session = getSession();
        session.getTransaction().begin();
        session.createQuery("delete Book where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        return true;
    }
}
