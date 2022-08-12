package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ChatroomRepositoryJdbcImpl{
	private Connection connection;
	private final String TEMPLATE = "SELECT * FROM chat.room WHERE id=?";
	MessagesRepositoryJdbcImpl messagesRepository;
	UserRepositoryJdbcImpl userRepository;


	public ChatroomRepositoryJdbcImpl(Connection connection, UserRepositoryJdbcImpl usr){
		this.connection = connection;
		this.userRepository = usr;
	}

	public Optional<Chatroom> findById(Long id) throws SQLException {
		Chatroom chat = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE);
		preparedStatement.setLong(1, id);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			chat = new Chatroom(
					resultSet.getLong("id"),
					resultSet.getString("name"),
					userRepository.findById(resultSet.getLong("owner")).orElse(null),
					new ArrayList<>()
			);
		}
		resultSet.close();
		preparedStatement.close();
		return (Optional.ofNullable(chat));
	}
}
