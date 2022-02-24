package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Long id_count = Long.valueOf("1");
    private Connection connection;

    {
        try{
            this.connection = Util.getMyConnection();
            this.connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
        }
    }

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE USERS (\n" +
                "   USER_ID bigint not null,\n" +
                "   USER_NAME varchar(255) not null,\n" +
                "   USER_LAST_NAME varchar(255) not null,\n" +
                "   USER_AGE tinyint not null,\n" +
                "   primary key (USER_ID),\n" +
                "   unique (USER_ID)\n" +
                ");";
        statementExecute(sql);
    }

    public void dropUsersTable() {
        statementExecute("drop table users;");
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO USERS (USER_ID, USER_NAME, USER_LAST_NAME, USER_AGE) " +
                "values (?, ?, ?, ?);";
        Savepoint savePoint2 = null;
        try{
            savePoint2 = this.connection.setSavepoint("savePoint2");
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setLong(1, id_count);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4, age);
            preparedStatement.executeUpdate();

            this.connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.getMessage();
            connectionRollback(savePoint2);
        }
        id_count++;
    }

    public void removeUserById(long id) {
        String sql = "delete from users where USER_ID = ?;";
        Savepoint savePoint3 = null;
        try{
            savePoint3 = connection.setSavepoint("savePoint3");
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            this.connection.commit();
        } catch (SQLException e){
            e.getMessage();
            connectionRollback(savePoint3);
        }
    }

    public List<User> getAllUsers() {
        String sql = "select * from users;";

        List<User> userList = new ArrayList<>();
        Savepoint savePoint4 = null;
        try{
            savePoint4 = this.connection.setSavepoint("savePoint4");
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            this.connection.commit();

            while(rs.next()){
                User user = new User(rs.getString(2), rs.getString(3), rs.getByte(4));
                user.setId(rs.getLong(1));
                userList.add(user);
            }
        }catch(SQLException e){
            e.getMessage();
            connectionRollback(savePoint4);
        }
        return userList;
    }

    public void cleanUsersTable() {
        statementExecute("delete from users;");
    }

    private boolean statementExecute(String sql){
        Savepoint savePoint1 = null;
        try{
            savePoint1 = this.connection.setSavepoint("savePoint1");
            this.connection.createStatement().execute(sql);

            this.connection.commit();
            return true;
        } catch (SQLException e) {
            e.getMessage();
            connectionRollback(savePoint1);
            return false;
        }
    }

    private void connectionRollback(Savepoint savePoint){
        try{
            this.connection.rollback(savePoint);
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void closeConnection(){
        try{
            this.connection.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
