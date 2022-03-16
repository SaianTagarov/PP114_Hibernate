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

//        usi.saveUser("Валентина", "Валентинова", (byte) 32);
//        usi.saveUser("Дмитрий", "Дмитриев", (byte) 37);
//        usi.saveUser("Роман", "Романов", (byte) 21);
//        usi.saveUser("Андрей", "Андреев", (byte) 49);


        List<User> listUser = usi.getAllUsers();
        listUser.stream().forEach(System.out::println);

        usi.removeUserById((long)1);
        usi.cleanUsersTable();
        usi.dropUsersTable();
        usi.closeSessionFactory();
    }
}
