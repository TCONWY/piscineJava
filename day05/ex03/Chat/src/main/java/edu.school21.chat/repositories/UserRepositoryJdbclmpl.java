package edu.school21.chat.repositories;

import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UserRepositoryJdbclmpl{

    private final Connection connection;
    private final String COMAND = "SELECT * FROM chat.user WHERE id=?";

    public UserRepositoryJdbclmpl(Connection connection) {
        this.connection = connection;
    }

    public Optional<User> findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(COMAND);
        statement.setLong(1, id);
        UserRepositoryJdbclmpl user = new UserRepositoryJdbclmpl(connection);
        ResultSet result = statement.executeQuery();
        User user1 = null;
        if(result.next()) {
            user1 = new User(result.getLong("id"),
                    result.getString("login"),
                    result.getString("password"),
                    new ArrayList<>(), new ArrayList<>());
        }
        result.close();
        statement.close();
        return Optional.ofNullable(user1);
    }
}