package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import java.sql.Connection;
import java.sql.SQLException;

public class ProductsRepositoryJdbcImplTest {
    private Connection connection;

    @BeforeEach
    public void resetConnection() throws SQLException {
        this.connection = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build().getConnection();
    }

    @Test
    public void testSave() throws SQLException {
        final Product EXPECTED_PRODUCT = new Product(null, "Stuff", 1000000L);

        ProductsRepositoryJdbcImpl repo = new ProductsRepositoryJdbcImpl(connection);
        repo.save(EXPECTED_PRODUCT);
        Assertions.assertNotNull(EXPECTED_PRODUCT.getId());
        Assertions.assertEquals(EXPECTED_PRODUCT, repo.findById(EXPECTED_PRODUCT.getId()).orElse(null));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void testUpdate(long id) throws SQLException {
        ProductsRepositoryJdbcImpl repo = new ProductsRepositoryJdbcImpl(connection);
        final Product EXPECTED_PRODUCT = repo.findById(id).orElse(null);

        assert EXPECTED_PRODUCT != null;
        EXPECTED_PRODUCT.setPrice(id);
        repo.update(EXPECTED_PRODUCT);
        Product result = repo.findById(id).orElse(null);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getPrice());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void testFindByIdTrue(long id) throws SQLException {
        ProductsRepositoryJdbcImpl repo = new ProductsRepositoryJdbcImpl(connection);
        Assertions.assertTrue(repo.findById(id).isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {777, 666, 999})
    public void testFindByIdFalse(long id) throws SQLException {
        ProductsRepositoryJdbcImpl repo = new ProductsRepositoryJdbcImpl(connection);
        Assertions.assertFalse(repo.findById(id).isPresent());
    }

    @Test
    public void testFindAll() throws SQLException {
        ProductsRepositoryJdbcImpl repo = new ProductsRepositoryJdbcImpl(connection);
        long EXPECTED_SIZE = 7;
        Assertions.assertEquals(EXPECTED_SIZE, repo.findAll().size());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5})
    public void testDelete(long id) throws SQLException {
        ProductsRepositoryJdbcImpl repo = new ProductsRepositoryJdbcImpl(connection);
        repo.delete(id);
        Assertions.assertFalse(repo.findById(id).isPresent());
    }
}