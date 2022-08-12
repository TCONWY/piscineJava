package school21.spring.service.repositories;

import org.springframework.jdbc.core.RowMapper;
import school21.spring.service.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    JdbcTemplate jdbcTemplate;
    private final String sqlFindAll = "SELECT * FROM users";
    private final String sqlFindById = "SELECT * FROM users WHERE id = ?";
    private final String sqlUpdate = "UPDATE users SET email = ? WHERE id = ?";
    private final String sqlSave = "INSERT INTO users (email) VALUES (?)";
    private final String sqlDelete = "DELETE FROM users WHERE id = ?";
    private final String sqlFindByEmail = "SELECT * FROM users WHERE email = ?";

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) ->
            new User(rs.getLong("id"), rs.getString("email")));

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
        jdbcTemplate.update(sqlSave, entity.getEmail());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(sqlUpdate, entity.getEmail(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(sqlDelete, id);
    }

    @Override
    public Optional findByEmail(String email) {
        List<User> users = jdbcTemplate.query(sqlFindByEmail, userRowMapper, email);
        if (users.isEmpty())
            return Optional.empty();
        return Optional.of(users.get(0));
    }
}
