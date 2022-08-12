package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;

    private final String sqlFindAll = "SELECT * FROM users";
    private final String sqlFindById = "SELECT * FROM users WHERE id = ?";
    private final String sqlUpdate = "UPDATE users SET email = ? WHERE id = ?";
    private final String sqlSave = "INSERT INTO users (email) VALUES (?)";
    private final String sqlDelete = "DELETE FROM users WHERE id = ?";
    private final String sqlFindByEmail = "SELECT * FROM users WHERE email = ?";
    private final String sqlFindPasswordById = "SELECT password FROM users WHERE id = ?";
    private final String sqlSavePassword = "Update users SET password = ? WHERE id = ?";
    private final String sqlSaveByEmail = "INSERT INTO users (email, password) VALUES (?, ?)";

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> userOptional = Optional.empty();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlFindById);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                String email = resultSet.getString("email");
                User user = new User(id, email);
                userOptional =Optional.of(user);
            }
            return userOptional;
        } catch (SQLException e) {
            try {
                throw new IllegalAccessException(e.getMessage());
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("error findById");
            }
        }
        return userOptional;
    }

    @Override
    public List<User> findAll() {
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlFindAll);
            rs = ps.executeQuery();
            while(rs.next()){
                users.add( new User(rs.getLong("id"), rs.getString("email")));
            }
        } catch (SQLException e) {
            System.err.println("error findAll");
        }
        return users;
    }

    @Override
    public void save(User entity) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlSave);
            statement.setString(1, entity.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("error save");
        }
    }

    @Override
    public String findPasswordById(Long id) {
        String password = "";
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlFindPasswordById);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                password = resultSet.getString("password");
            return password;
        } catch (SQLException e) {
            System.err.println("error findPasswordById");
        }
        return "";
    }

    @Override
    public void savePassword(User user, String password) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlSavePassword);
            statement.setLong(2, user.getId());
            statement.setString(1, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("error savePasswords");
        }
    }

    @Override
    public void saveByEmail(String email, String password) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlSaveByEmail);
            statement.setString(1,email);
            statement.setString(2 , password);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("error saveByEmail");
        }
    }

    @Override
    public void update(User entity) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setLong(2, entity.getId());
            statement.setString(1, entity.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("error update");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlDelete);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("error delete");
        }
    }

    @Override
    public Optional findByEmail(String email) {
        Optional<User> userOptional = Optional.empty();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlFindByEmail);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                Long id = resultSet.getLong("id");
                User user = new User(id, email);
                userOptional = Optional.of(user);
            }
            return userOptional;
        } catch (SQLException e) {
            try {
                throw new IllegalAccessException(e.getMessage());
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("error findByEmail");
            }
        }
        return userOptional;
    }
}
