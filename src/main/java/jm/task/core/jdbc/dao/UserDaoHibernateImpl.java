package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.GetTransaction;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        String sql = "CREATE TABLE USERS (\n" +
                "   USER_ID bigint not null auto_increment,\n" +
                "   USER_NAME varchar(255) not null,\n" +
                "   USER_LAST_NAME varchar(255) not null,\n" +
                "   USER_AGE tinyint not null,\n" +
                "   primary key (USER_ID),\n" +
                "   unique (USER_ID)\n" +
                ");";
        ((GetTransaction) () -> session.createSQLQuery(sql).executeUpdate()).getTrans(session);
    }

    @Override
    public void dropUsersTable() {
        Session session = this.sessionFactory.getCurrentSession();
        String sql = "DROP TABLE USERS;";
        ((GetTransaction) () -> session.createSQLQuery(sql).executeUpdate()).getTrans(session);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = this.sessionFactory.getCurrentSession();
        ((GetTransaction) () -> session.save(new User(name, lastName, age))).getTrans(session);
    }

    @Override
    public void removeUserById(long id1) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        ((GetTransaction) () -> {
            Query<User> query = session.createQuery("from User where id0 = :id2");
            query.setParameter("id2", id1);
            User userForRemove = query.uniqueResult();
            if (userForRemove != null) {
                session.remove(userForRemove);
            }
        }).getTrans(session);
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        final List<User>[] listUser = new List[]{new ArrayList<>()};
        ((GetTransaction) () -> listUser[0] = session.createQuery("select i from User i", User.class)
                .getResultList()).getTrans(session);
        return listUser[0];
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        final List<User>[] listUser = new List[1];
        ((GetTransaction) () -> {
            listUser[0] = session.createQuery("select i from User i", User.class).getResultList();
            listUser[0].stream().forEach(e -> session.remove(e));
        }).getTrans(session);
    }

    public void closeSessionFactory(){
        this.sessionFactory.close();
    }
}
