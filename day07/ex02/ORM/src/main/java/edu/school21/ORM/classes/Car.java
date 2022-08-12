package edu.school21.ORM.classes;

import edu.school21.ORM.annotations.OrmColumn;
import edu.school21.ORM.annotations.OrmColumnId;
import edu.school21.ORM.annotations.OrmEntity;

@OrmEntity(table = "Car")
public class Car {
	@OrmColumn(name = "model", length = 150)
	private String model;
	@OrmColumn(name = "color_car", length = 150)
	private String color_car;
	@OrmColumnId
	private Long id;

	public Car(){
	}

	public Car(String name, String color, Long id) {
		this.model = name;
		this.color_car = color;
		this.id = id;
	}

	public void start() {
		System.out.println("brrr");
	}

	@Override
	public String toString() {
		return "Car{" +
				"model='" + model + '\'' +
				", color_car='" + color_car + '\'' +
				", id=" + id +
				'}';
	}
}
