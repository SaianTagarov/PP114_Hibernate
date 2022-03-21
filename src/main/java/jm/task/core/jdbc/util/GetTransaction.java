package jm.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

public interface GetTransaction {
    void setBodyMethod();
    default void getTrans(Session session){
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            setBodyMethod();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.getMessage();
        } finally {
            session.close();
        }
    }
}
