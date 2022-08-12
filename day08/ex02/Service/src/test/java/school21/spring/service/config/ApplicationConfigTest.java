package school21.spring.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages =  "school21.spring.service")
public class ApplicationConfigTest{
    private EmbeddedDatabase dataSource;

    private final String sqlDropTable =
            "DROP TABLE users IF EXISTS; ";

    private final String sqlCreateTable =
            "CREATE TABLE users (id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, email VARCHAR(150), password VARCHAR(32))";

    @Bean
    public DataSource hsqlDataSource(){
        try {
            dataSource = new EmbeddedDatabaseBuilder().build();
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlDropTable);
            statement.execute();
            statement = connection.prepareStatement(sqlCreateTable);
            statement.execute();
            return dataSource;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate() {
        return new UsersRepositoryJdbcTemplateImpl(hsqlDataSource());
    }

    @Bean
    public UsersRepositoryJdbcImpl usersRepositoryJdbc() {
        return new UsersRepositoryJdbcImpl(hsqlDataSource());
    }
}