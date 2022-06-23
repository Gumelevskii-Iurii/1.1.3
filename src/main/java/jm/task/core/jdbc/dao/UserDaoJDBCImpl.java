package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

       try (Statement stmt = Util.getConnection().createStatement()){
           stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(64), lastname VARCHAR(64), age INT)");

       }  catch (SQLException e) {
           e.printStackTrace();
       }
    }

    public void dropUsersTable() {
        try (Statement stmt = Util.getConnection().createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS users ");
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement pstmt = Util.getConnection().prepareStatement("INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)")){
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);

            pstmt.executeUpdate();

            System.out.println("User with name – " + name + " добавлен в базу данных");
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String string = "DELETE FROM users WHERE ID = ?";

        try (PreparedStatement pstmt = Util.getConnection().prepareStatement(string)){
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String string2 = "SELECT * FROM users";
        try (PreparedStatement pstmt = Util.getConnection().prepareStatement(string2)){
            ResultSet resultSet = pstmt.executeQuery(string2);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);

            }
            pstmt.executeQuery();
        }  catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Statement stmt = Util.getConnection().createStatement()) {
            stmt.execute("TRUNCATE TABLE users");
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
