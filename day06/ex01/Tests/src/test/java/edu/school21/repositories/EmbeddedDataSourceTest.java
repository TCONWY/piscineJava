package edu.school21.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;


public class EmbeddedDataSourceTest {
	private DataSource ds;
	@BeforeEach
	public void init() {
		EmbeddedDatabaseBuilder DS = new EmbeddedDatabaseBuilder();
		ds = DS.setType(EmbeddedDatabaseType.HSQL)
				.addScript("schema.sql")
				.addScript("data.sql")
				.build();
	}
	@Test
	public void testConnection() throws SQLException {
		Assertions.assertNotNull(ds.getConnection());
	}
}
