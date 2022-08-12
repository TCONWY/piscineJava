package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ChatroomRepositoryJdbclmpl{

    private final Connection connection;
    private final String COMAND = "SELECT * FROM chat.room WHERE id=?";

    public ChatroomRepositoryJdbclmpl(Connection connection) {
        this.connection = connection;
    }

    public Optional<Chatroom> findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(COMAND);
        statement.setLong(1, id);
        UserRepositoryJdbclmpl user = new UserRepositoryJdbclmpl(connection);
        ResultSet reulst = statement.executeQuery();
        Chatroom chatroom = null;
        if(reulst.next()) {
            chatroom = new Chatroom(reulst.getLong("id"),
                    reulst.getString("name"),
                    user.findById(reulst.getLong("owner")).orElse(null),
                    new ArrayList<>());
        }
        reulst.close();
        statement.close();
        return Optional.ofNullable(chatroom);
    }
}