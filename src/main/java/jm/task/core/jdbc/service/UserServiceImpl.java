package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.dao.*;
import java.util.List;


public class UserServiceImpl implements UserService {

    private UserDao udhi;

    public UserServiceImpl() {
        this.udhi = new UserDaoHibernateImpl();
    }

    public void createUsersTable()  {
        this.udhi.createUsersTable();
    }

    public void dropUsersTable() {
        this.udhi.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        this.udhi.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        this.udhi.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return this.udhi.getAllUsers();
    }

    public void cleanUsersTable() {
        this.udhi.cleanUsersTable();
    }
    public void closeSessionFactory(){
        this.udhi.closeSessionFactory();
    }
}