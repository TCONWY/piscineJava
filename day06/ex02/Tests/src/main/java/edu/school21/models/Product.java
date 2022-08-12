package edu.school21.models;


public class Product {
	private Long id;
	private String name;
	private Long price;

	public Product(Long id, String name, Long price){
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getPrice() {
		return price;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		Product product = (Product) obj;
		return id == product.getId() &&
				name.equals(product.name) &&
				price.equals(product.price);
	}

	@Override
	public String toString() {
		return  "Products{id = " + id + ", name = " + name + ", price = " + price + "}";
	}
}
