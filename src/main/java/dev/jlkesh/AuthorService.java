package dev.jlkesh;

import org.hibernate.Session;

public class AuthorService extends CRUDService<Author, String> {
    @Override
    public Author save(Author author) {
        Session session = getSession();
        session.getTransaction().begin();
        session.persist(author);
        session.getTransaction().commit();
        return author;
    }

    @Override
    public Author get(String id) {
        Session session = getSession();
        session.getTransaction().begin();
        Author author = session.find(Author.class, id);
        session.getTransaction().commit();
        return author;
    }

    @Override
    public boolean update(Author author) {
        return false;
    }

    @Override
    public boolean delete(Author author) {
        Session session = getSession();
        session.getTransaction().begin();
        session.remove(author);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }
}
