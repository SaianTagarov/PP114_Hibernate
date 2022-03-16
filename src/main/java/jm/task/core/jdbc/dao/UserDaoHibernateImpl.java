package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory;

    {
        this.sessionFactory = Util.getSessionFactory();
    }

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();

        String sql = "CREATE TABLE USERS (\n" +
                "   USER_ID bigint not null auto_increment,\n" +
                "   USER_NAME varchar(255) not null,\n" +
                "   USER_LAST_NAME varchar(255) not null,\n" +
                "   USER_AGE tinyint not null,\n" +
                "   primary key (USER_ID),\n" +
                "   unique (USER_ID)\n" +
                ");";
        try {
            session.createSQLQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.getMessage();
        }

        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();

        String sql = "DROP TABLE USERS;";

        try {
            session.createSQLQuery(sql).executeUpdate();
        } catch( Exception e) {
            e.getMessage();
        }
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));

        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id1) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        Query<User> query = session.createQuery("from User where id0 = :id2");
        query.setParameter("id2", id1);
        User userForRemove = query.uniqueResult();

        if(userForRemove != null){
            session.remove(userForRemove);
        }
        session.getTransaction().commit();

    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        List<User> listUser = new ArrayList<>();
        session.beginTransaction();
        try {
            listUser = session.createQuery("select i from User i", User.class).getResultList();
        } catch (Exception e) {
            e.getMessage();
        }
        session.getTransaction().commit();
        return listUser;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        List<User> listUser = new ArrayList<>();
        session.beginTransaction();
        try {
            listUser = session.createQuery("select i from User i", User.class).getResultList();
        } catch (Exception e) {
            e.getMessage();
        }
        listUser.stream().forEach(e -> session.remove(e));
        session.getTransaction().commit();
    }

    public void closeSessionFactory(){
        this.sessionFactory.close();
    }
}
