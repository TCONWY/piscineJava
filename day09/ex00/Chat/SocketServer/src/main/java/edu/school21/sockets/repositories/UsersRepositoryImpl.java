package edu.school21.sockets.repositories;
import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.jws.soap.SOAPBinding;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository {
    JdbcTemplate jdbcTemplate;
    private final String sqlFindAll = "SELECT * FROM users";
    private final String sqlFindById = "SELECT * FROM users WHERE id = ?";
    private final String sqlUpdate = "UPDATE users SET name = ? WHERE id = ?";
    private final String sqlSave = "INSERT INTO users (names) VALUES (?)";
    private final String sqlDelete = "DELETE FROM users WHERE id = ?";
    private final String sqlFindByName = "SELECT * FROM users WHERE name = ?";
    private final String sqlFindPasswordById = "SELECT password FROM users WHERE id = ?";
    private final String sqlSavePassword = "Update users SET password = ? WHERE id = ?";
    private final String sqlSaveByName = "INSERT INTO users (name, password) VALUES (?, ?)";

    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) ->
            new User(rs.getLong("id"), rs.getString("name")));

    private RowMapper<String> stringRowMapper = (rs, rowNum) ->
            rs.getString("password");
    @Override
    public Optional<User> findById(Long id) {
        List<User> users = jdbcTemplate.query(sqlFindById, userRowMapper, id);
        if (users.isEmpty())
            return Optional.empty();
        return Optional.of(users.get(0));
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(sqlFindAll, userRowMapper);
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(sqlSave, entity.getName());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(sqlUpdate, entity.getName(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(sqlDelete, id);
    }

    @Override
    public Optional findByName(String name) {
        List<User> users = jdbcTemplate.query(sqlFindByName, userRowMapper, name);
        if (users.isEmpty())
            return Optional.empty();
        return Optional.of(users.get(0));
    }

    @Override
    public String findPasswordById(Long id) {
        List<String> passwords =  jdbcTemplate.query(sqlFindPasswordById, stringRowMapper, id);
        if (passwords.isEmpty())
            return "";
        return passwords.get(0);
    }

    @Override
    public void savePassword(User user, String password) {
        jdbcTemplate.update(sqlSavePassword, password, user.getId());
    }

    @Override
    public void saveByEmail(String name, String password) {
        jdbcTemplate.update(sqlSaveByName, name, password);
    }
}
