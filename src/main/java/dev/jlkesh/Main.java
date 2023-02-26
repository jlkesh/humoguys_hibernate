package dev.jlkesh;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

  /*      Book book = Book.builder()
                .title("Otamdan Qolgan Dalalar")
                .author("Primqul Qodirov")
                .build();
        Book book2 = Book.builder()
                .title("Yulduzli Tunlar")
                .author("Primqul Qodirov")
                .build();
        session.persist(book);
        session.persist(book2);*/
        /*Query<Book> query = session.createQuery("select t from book t", Book.class);
        List<Book> books = query.getResultList();
        books.forEach(System.out::println);*/
        Book book = (Book) session.getNamedNativeQuery("findByIdNative")
                .setParameter("id", "ff808181868d17e901868d17eb5f0000")
                .getSingleResult();
        /*Book book = session.find(Book.class, "ff808181868d17e901868d17eb5f0000");*/
        /*session.createQuery("delete book t where t.id = :id ")
                .setParameter("id", "ff808181868d1f4001868d1f41d60001")
                .executeUpdate();*/
        /*System.out.println(book);*/
        /*book.setTitle("Spring IN ACTION BY HUMOGUYS");
        session.merge(book);*/
        System.out.println(book);
        transaction.commit();
        session.close();
    }

}