package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    public static final String FIND_BY_ID = "SELECT FROM message WHERE id=?";
    public static final String FIND_ALL = "SELECT * FROM message";
    public static final String SAVE = "INSERT INTO message (text, time) VALUES (?, ?)";
    public static final String UPDATE = "UPDATE message SET text=?, time=? WHERE id=?";
    public static final String DELETE = "DELETE FROM message WHERE id=?";
    private final JdbcTemplate jdbcTemplate;

    public MessageRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Message findById(Long id) {
        return jdbcTemplate.query(
                        FIND_BY_ID,
                new Object[]{id},
                new int[]{},
                new BeanPropertyRowMapper<>(Message.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query(FIND_ALL,
                new BeanPropertyRowMapper<>(Message.class));
    }

    @Override
    public void save(Message entity) {
        jdbcTemplate.update(SAVE,
                entity.getText(),
                entity.getTime());
    }

    @Override
    public void update(Message entity) {
        jdbcTemplate.update(UPDATE,
                entity.getText(),
                entity.getTime(),
                entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }
}
