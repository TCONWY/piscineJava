package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository{
    private final Connection dataSource;

    public ProductsRepositoryJdbcImpl(Connection dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String COMAND = "SELECT * FROM product";
        ResultSet rs = null;
        List<Product> products = new ArrayList<>();
        PreparedStatement ps = dataSource.prepareStatement(COMAND);
        rs = ps.executeQuery();
        while(rs.next()){
            products.add(new Product(rs.getLong("id"),
                    rs.getString("name"),
                    rs.getLong("price")));
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) throws SQLException {
        String COMAND = "SELECT * FROM product WHERE id=?";
        PreparedStatement statement = null;
        Product product = null;
        statement = dataSource.prepareStatement(COMAND);
        statement.setLong(1, id);
        ResultSet reulst = statement.executeQuery();
        if (reulst.next()) {
            product = new Product(reulst.getLong("id"),
                    reulst.getString("name"),
                    reulst.getLong("price"));
        }
        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product) throws SQLException {
        final String QUERY_TEMPLATE = "UPDATE product SET " + "name = ?, " +
                "price = ? " +
                " WHERE id = ?";
        PreparedStatement query = dataSource.prepareStatement(QUERY_TEMPLATE);
        query.setString(1, product.getName());
        query.setLong(2, product.getPrice());
        query.setLong(3, product.getId());
        query.execute();
    }

    @Override
    public void save(Product product) throws SQLException {
        final String QUERY_TEMPLATE = "INSERT INTO PRODUCT (name, price) VALUES (?, ?)";

        ResultSet resultSet = null;
        PreparedStatement query = dataSource.prepareStatement(QUERY_TEMPLATE);
        query.setString(1, product.getName());
        query.setLong(2, product.getPrice());
        query.execute();
        resultSet = dataSource.createStatement().executeQuery("CALL IDENTITY()");
        if(resultSet.next())
            product.setId(resultSet.getLong(1));
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM product WHERE id=?";
        if (findById(id).isPresent()) {
            PreparedStatement statement = dataSource.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
        }
    }
}