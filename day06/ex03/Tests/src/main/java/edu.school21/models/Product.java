package edu.school21.models;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private Long price;
    Connection connection;
    DataSource ds;

    public Long getId() {
        return id;
    }

    public Product(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product product = (Product) obj;
        return id == product.getId() &&
                name.equals(product.getName()) &&
                price.equals(product.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                "}";
    }
}