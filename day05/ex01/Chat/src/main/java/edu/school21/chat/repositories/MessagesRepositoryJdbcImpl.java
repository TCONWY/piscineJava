package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;

import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{
	private Connection connection;
	private final String TEMPLATE = "SELECT * FROM chat.message WHERE id=?";
	UserRepositoryJdbcImpl userRepository;
	ChatroomRepositoryJdbcImpl chatroomRepository;


	public MessagesRepositoryJdbcImpl(Connection connection, UserRepositoryJdbcImpl usr, ChatroomRepositoryJdbcImpl room){
		this.connection = connection;
		this.userRepository = usr;
		this.chatroomRepository = room;
	}

	@Override
	public Optional<Message> findById(Long id) throws SQLException {
		Message mes = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE);
		preparedStatement.setLong(1, id);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			mes = new Message(
					resultSet.getLong("id"),
					userRepository.findById(resultSet.getLong("author")).orElse(null),
					chatroomRepository.findById(resultSet.getLong("room")).orElse(null),
					resultSet.getString("text_message"),
					resultSet.getTimestamp("timestamp").toLocalDateTime()
			);
		}
		resultSet.close();
		preparedStatement.close();
		return (Optional.ofNullable(mes));
	}
}
