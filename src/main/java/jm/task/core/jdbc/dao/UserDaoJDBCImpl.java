package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
   // private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String createTable = ("CREATE TABLE IF NOT EXISTS users" +
                    "(id mediumint not null auto_increment," +
                    "name VARCHAR(50)," +
                    "lastname VARCHAR(50)," +
                    "age tinyint," +
                    "PRIMARY KEY (id))");
            statement.execute(createTable);
            System.out.println("Таблица создана");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String dropTable = "DROP TABLE IF EXISTS users ";
            statement.execute(dropTable);
            System.out.println("Таблица удалена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        String save = "INSERT INTO users(name,lastname,age) VALUES(?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(save);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String removeId = "DELETE FROM users WHERE id";
            statement.execute(removeId);
            System.out.println("User удален");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public List<User> getAllUsers() {
        Connection connection = Util.getConnection();
        List<User> allUsers = new ArrayList<>();
        User user = new User();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String getAllUsers = "SELECT id,name,lastname,age FROM users";
            resultSet = statement.executeQuery(getAllUsers);
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                }
            }
            return allUsers;
        }
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String cleanUserTable = "TRUNCATE users";
            statement.executeUpdate(cleanUserTable);
            System.out.println("Таблица очищена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
