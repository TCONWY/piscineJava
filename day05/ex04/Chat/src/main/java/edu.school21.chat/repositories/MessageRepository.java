package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MessageRepository {
    Optional<Message> findById(Long id) throws SQLException;
    void save(Message message) throws SQLException;
    void update(Message message) throws SQLException;
}