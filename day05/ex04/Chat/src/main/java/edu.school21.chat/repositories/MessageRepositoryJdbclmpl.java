package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedException;
import edu.school21.chat.models.Message;

import java.sql.*;
import java.util.Optional;

public class MessageRepositoryJdbclmpl implements MessageRepository{

    private final Connection connection;
    private final String COMAND = "SELECT * FROM chat.message WHERE id=?";

    public MessageRepositoryJdbclmpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(COMAND);
        statement.setLong(1, id);
        UserRepositoryJdbclmpl author = new UserRepositoryJdbclmpl(connection);
        ChatroomRepositoryJdbclmpl room = new ChatroomRepositoryJdbclmpl(connection);
        ResultSet reulst = statement.executeQuery();
        Message message = null;
        if(reulst.next()){
            message = new Message(reulst.getLong("id"),
                    author.findById(reulst.getLong("author")).orElse(null),
                    room.findById((reulst.getLong("room"))).orElse(null),
                    reulst.getString("text_message"),
                    reulst.getTimestamp("timestamp").toLocalDateTime());
        }
        statement.close();
        reulst.close();
        return Optional.ofNullable(message);
    }

    @Override
    public void save(Message message) throws SQLException {
        final String QUERY_TEMPLATE = "INSERT INTO chat.message (author, room, text_message, timestamp) VALUES (?, ?, ?, ?) RETURNING *";

        ResultSet resultSet = null;
        UserRepositoryJdbclmpl user = new UserRepositoryJdbclmpl(connection);
        ChatroomRepositoryJdbclmpl chat = new ChatroomRepositoryJdbclmpl(connection);
        PreparedStatement query = null;
        if(user.findById(message.getAuthor().getId()).isPresent() || chat.findById(message.getRoom().getId()).isPresent()) {
            if (!user.findById(message.getAuthor().getId()).get().equals(message.getAuthor())
                    || !chat.findById(message.getRoom().getId()).get().equals(message.getRoom()))
                throw new NotSavedException();
        } else {
            throw new NotSavedException();
        }
        try {
            query = connection.prepareStatement(QUERY_TEMPLATE);
            query.setLong(1, message.getAuthor().getId());
            query.setLong(2, message.getRoom().getId());
            query.setString(3, message.getText());
            query.setTimestamp(4, Timestamp.valueOf(message.getTime()));
            resultSet = query.executeQuery();
            resultSet.next();
            message.setId(resultSet.getLong("id"));
        } catch (SQLException e) {
            throw new NotSavedException();
        } finally {
            if (query != null) {
                query.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    @Override
    public void update(Message message) throws SQLException {
        final String QUERY_TEMPLATE = "UPDATE chat.message SET " + "author = ?, " +
                "room = ?, " +
                "text_message = ?, " +
                "timestamp = ? "
                +" WHERE id = ?";

        UserRepositoryJdbclmpl user = new UserRepositoryJdbclmpl(connection);
        ChatroomRepositoryJdbclmpl chat = new ChatroomRepositoryJdbclmpl(connection);
        PreparedStatement query = null;
        try {
            query = connection.prepareStatement(QUERY_TEMPLATE);
            query.setLong(1, message.getAuthor().getId());
            query.setLong(2, message.getRoom().getId());
            query.setString(3, message.getText());
            try {
                query.setTimestamp(4, Timestamp.valueOf(message.getTime()));
            } catch (NullPointerException e){
                query.setTimestamp(4, null);
            }
            query.setLong(5, message.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (query != null) {
                query.close();
            }
        }
    }
}