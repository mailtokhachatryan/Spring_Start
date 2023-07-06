package am.myOffice.shopJDBC.repository.user.impl;

import am.myOffice.shopJDBC.exceptions.UserNotFoundException;
import am.myOffice.shopJDBC.exceptions.ValidationException;
import am.myOffice.shopJDBC.model.User;
import am.myOffice.shopJDBC.repository.user.UserRepository;
import am.myOffice.shopJDBC.util.DataSource;
import am.myOffice.shopJDBC.util.DatabaseConnection;
import am.myOffice.shopJDBC.util.constants.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final Connection connection;

    public UserRepositoryJdbcImpl(DatabaseConnection dataSource) {
        this.connection = dataSource.getConnection();
        try {
            this.connection.createStatement().executeUpdate(
                    """
                            CREATE TABLE IF NOT EXISTS users (
                            id bigserial primary key,
                            name varchar(255) not null,
                            lastname varchar(255) not null,
                            balance double precision not null,
                            email varchar(255) not null ,
                            password varchar(255) not null,
                            age integer not null
                            )
                            """);
        } catch (SQLException e) {
            System.out.println("Connection exception");
        }

    }


    public void init(){
        System.out.println("UserRepositoryJdbc init method executed");
    }

    @Override
    public void create(User user) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO users (name,lastname, balance,email,password,age) VALUES (?,?,?,?,?,?)"
            );
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setDouble(3, user.getBalance());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getAge());

            var i = preparedStatement.executeUpdate();
            if (i == 0)
                throw new Exception("something is wrong");
            preparedStatement.close();
        }catch (Exception e){
            throw new ValidationException(Message.REGISTRATION_IS_FAILED);
        }

    }

    @Override
    public void update(User user){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE users SET name = ?, lastname = ?, balance = ?, email = ?, password = ?, age = ? WHERE id = ?"
            );
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setDouble(3, user.getBalance());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getAge());
            preparedStatement.setLong(7, user.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public User get(Long id){
        User user = new User();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("SELECT * from users WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setUserFields(user, resultSet);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> usersList = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = connection.createStatement().executeQuery("SELECT * from users");
            addUserToListFromResultSet(usersList, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }


    @Override
    public List<User> findUsersByName(String name) {

        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE lower(name) LIKE lower(concat('%',?,'%'))"
            );
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addUserToListFromResultSet(users, resultSet);
        return users;
    }

    @Override
    public void delete(Long id) {

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("DELETE from users WHERE id = ?");
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findUsersByEmail(String email) {
        User user = null;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ?");
            preparedStatement.setString(1,email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = new User();
                setUserFields(user,resultSet);
            }
            else
                throw new UserNotFoundException(Message.USER_NOT_FOUNT);

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public void isUserExists(String email) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ?");
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                throw new ValidationException(Message.USER_WITH_THIS_EMAIL_IS_ALREADY_EXISTS);
        } catch (Exception e) {
            throw new ValidationException(Message.USER_WITH_THIS_EMAIL_IS_ALREADY_EXISTS);
        }
    }

    private void setUserFields(User user, ResultSet resultSet){
        try {
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setLastname(resultSet.getString("lastname"));
            user.setBalance(resultSet.getDouble("balance"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setAge(resultSet.getInt("age"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addUserToListFromResultSet(List<User> usersList, ResultSet resultSet) {
        while (true) {
            try {
                if (!resultSet.next()) break;
                User user = new User();
                setUserFields(user, resultSet);
                usersList.add(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}