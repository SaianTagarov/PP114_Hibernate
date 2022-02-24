package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;


import java.util.List;

public class Main {

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl usi = new UserServiceImpl();
        usi.createUsersTable();

        usi.saveUser("Пётр", "Петров", (byte) 25);
        usi.saveUser("Иван", "Иванов", (byte) 35);
        usi.saveUser("Василий", "Васильев", (byte) 52);
        usi.saveUser("Алексей", "Алексеев", (byte) 39);

        List<User> userList = usi.getAllUsers();
        userList.stream().forEach(System.out::println);

        //usi.removeUserById((long)4);
        usi.cleanUsersTable();
        usi.dropUsersTable();
        usi.closeConnection();
    }
}
