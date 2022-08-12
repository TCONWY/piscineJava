package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.repositories.ChatroomRepositoryJdbcImpl;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.UserRepositoryJdbcImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

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
		UserRepositoryJdbcImpl userRep = new UserRepositoryJdbcImpl(connection);
		ChatroomRepositoryJdbcImpl chatRep = new ChatroomRepositoryJdbcImpl(connection, userRep);
		MessagesRepositoryJdbcImpl messRep = new MessagesRepositoryJdbcImpl(connection, userRep, chatRep);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a message ID:");
		Long id = scanner.nextLong();
		System.out.println(messRep.findById(id).orElse(null));
		connection.close();
		scanner.close();
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
