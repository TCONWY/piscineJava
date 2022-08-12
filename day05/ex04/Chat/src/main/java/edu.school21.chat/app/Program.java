package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.ChatroomRepositoryJdbclmpl;
import edu.school21.chat.repositories.MessageRepositoryJdbclmpl;
import edu.school21.chat.repositories.UserRepositoryJdbclmpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

public class Program {

    private static final String DB_URL = "jdbc:postgresql://localhost/postgres";
    private static final String DB_USER = "postgres";
    private static final String BD_PWD = "";
    private static final String DB_SCHEMA = "../src/main/resources/schema.sql";
    private static final String DB_DATA = "../src/main/resources/data.sql";

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        Connection connection = connect();
        runQueriesFromFile(connection, DB_SCHEMA);
        runQueriesFromFile(connection, DB_DATA);

        UserRepositoryJdbclmpl us = new UserRepositoryJdbclmpl(connection);
        ChatroomRepositoryJdbclmpl ch = new ChatroomRepositoryJdbclmpl(connection);
        MessageRepositoryJdbclmpl mes = new MessageRepositoryJdbclmpl(connection);
        Message message = null;
        try {
            message = new Message(-1L, us.findById(3L).get(), ch.findById(3L).get(), "Hello!", LocalDateTime.now());
            mes.save(message);

            Optional<Message> messageOptional = mes.findById(5L);
            List<User> users = us.findAll(5, 1);
            for (int i = 0; i < users.size(); i++){
                System.out.println(users.get(i));
            }
            System.out.println(message.getId());
        } catch (NoSuchElementException e) {
            System.out.println("Error");
        }
        connection.close();
    }

    public static Connection connect(){
        Connection conn = null;
        try {
            conn = HikariConnect().getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static HikariDataSource HikariConnect() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(DB_URL);
        hikariConfig.setUsername(DB_USER);
        hikariConfig.setPassword(BD_PWD);
        return (new HikariDataSource(hikariConfig));
    }

    public static void runQueriesFromFile(Connection connection, String filename) throws FileNotFoundException, SQLException {
        try (Scanner scanner = new Scanner(new File(filename)).useDelimiter(";"); Statement statement = connection.createStatement()) {
            try {
                while (scanner.hasNext()) {
                    statement.execute(scanner.next().trim());
                }
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}