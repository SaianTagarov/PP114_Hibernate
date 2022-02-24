package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.dao.*;
import java.util.List;


public class UserServiceImpl implements UserService {

    private UserDaoJDBCImpl udj;

    public UserServiceImpl() {
        this.udj = new UserDaoJDBCImpl();
    }

    public void createUsersTable()  {
        this.udj.createUsersTable();
    }

    public void dropUsersTable() {
        this.udj.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        this.udj.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        this.udj.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return this.udj.getAllUsers();
    }

    public void cleanUsersTable() {
        this.udj.cleanUsersTable();
    }
    public void closeConnection(){
        this.udj.closeConnection();
    }
}