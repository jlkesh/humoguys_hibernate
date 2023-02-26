package dev.jlkesh;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class CRUDService<E, ID> {
    abstract E save(E e);

    abstract E get(ID id);

    abstract boolean update(E e);

    abstract boolean delete(E e);

    abstract boolean deleteById(ID id);


    protected Session getSession() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        return sessionFactory.getCurrentSession();
    }
}
