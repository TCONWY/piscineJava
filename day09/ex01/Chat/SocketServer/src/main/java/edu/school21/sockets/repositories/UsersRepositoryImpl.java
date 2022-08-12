package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static java.sql.Types.BIGINT;
import static java.sql.Types.VARCHAR;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    public static final String FIND_BY_ID = "SELECT * FROM users WHERE identifier=?";
    public static final String FIND_ALL = "SELECT * FROM users";
    public static final String FIND_BY_NAME = "SELECT * FROM users WHERE name=?";
    public static final String SAVE = "INSERT INTO users (name, password) VALUES (?, ?)";
    public static final String UPDATE = "UPDATE users SET name=?, password=? WHERE identifier=?";
    public static final String DELETE = "DELETE FROM users WHERE identifier=?";

    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate
                .query(FIND_BY_ID,
                        new Object[]{id},
                        new int[]{BIGINT},
                        new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate
                .query(FIND_ALL,
                        new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(SAVE,
                entity.getName(),
                entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(UPDATE,
                entity.getName(),
                entity.getPassword(),
                entity.getIdentifier());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return jdbcTemplate
                .query(FIND_BY_NAME,
                        new Object[]{name},
                        new int[]{VARCHAR},
                        new BeanPropertyRowMapper<>(User.class))
                .stream().findAny();
    }
}
