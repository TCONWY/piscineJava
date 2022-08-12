package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository{
	private final Connection ds;

	public ProductsRepositoryJdbcImpl(Connection ds) {
		this.ds = ds;
	}

	@Override
	public List<Product> findAll() throws SQLException {
		final String COMMAND = "SELECT * FROM product";
		PreparedStatement preparedStatement = null;
		preparedStatement = ds.prepareStatement(COMMAND);
		ResultSet result = preparedStatement.executeQuery();
		List<Product> products = new ArrayList<>();
		while (result.next()) {
			products.add(new Product(result.getLong("id"), result.getString("name"), result.getLong("price")));
		}
		return products;
	}

	@Override
	public Optional<Product> findById(Long id) throws SQLException {
		final String COMMAND = "SELECT * FROM product WHERE id=?";
		PreparedStatement preparedStatement = null;
		preparedStatement = ds.prepareStatement(COMMAND);
		preparedStatement.setLong(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		Product product= null;
		if(resultSet.next()){
			product = new Product(resultSet.getLong("id"),
					resultSet.getString("name"),
					resultSet.getLong("price"));
		}
		return Optional.ofNullable(product);
	}

	@Override
	public void update(Product product) throws SQLException {
		final String COMMAND = "UPDATE product SET name = ?, price = ? WHERE id = ?";
		PreparedStatement preparedStatement = ds.prepareStatement(COMMAND);
		preparedStatement.setString(1, product.getName());
		preparedStatement.setLong(2, product.getPrice());
		preparedStatement.setLong(3, product.getId());
		preparedStatement.execute();
	}

	@Override
	public void save(Product product) throws SQLException {
		final String COMMAND = "INSERT INTO product (name, price) VALUES (?, ?)";
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = ds.prepareStatement(COMMAND);
		preparedStatement.setLong(2, product.getPrice());
		preparedStatement.setString(1, product.getName());
		preparedStatement.execute();
		resultSet = ds.createStatement().executeQuery("CALL IDENTITY()");
		if (resultSet.next()) {
			product.setId(resultSet.getLong(1));
		}
	}

	@Override
	public void delete(Long id) throws SQLException {
		final String COMMAND = "DELETE FROM product WHERE id=?";
		if(findById(id).isPresent()){
			PreparedStatement statement = ds.prepareStatement(COMMAND);
			statement.setLong(1, id);
			statement.execute();
		}
	}
}
