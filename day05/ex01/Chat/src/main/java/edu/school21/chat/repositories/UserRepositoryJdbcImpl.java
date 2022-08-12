package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UserRepositoryJdbcImpl{
	private Connection connection;
	private final String TEMPLATE = "SELECT * FROM chat.user WHERE id=?";
	MessagesRepositoryJdbcImpl messagesRepository;
	ChatroomRepositoryJdbcImpl chatroomRepository;


	public UserRepositoryJdbcImpl(Connection connection){
		this.connection = connection;
	}

	public Optional<User> findById(Long id) throws SQLException {
		User usr = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE);
		preparedStatement.setLong(1, id);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			usr = new User(
					resultSet.getLong("id"),
					resultSet.getString("login"),
					resultSet.getString("password"),
					new ArrayList<>(),
					new ArrayList<>()
					);
		}
		resultSet.close();
		preparedStatement.close();
		return (Optional.ofNullable(usr));
	}
}
