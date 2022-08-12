package edu.school21.app.classes;

public class Car {
	private String color;

	public Car(String color) {
		this.color = color;
	}
	public Car(){
	}

	public void start() {
		System.out.println("brrr");
	}

	@Override
	public String toString() {
		return "Car{color=" + color + '}';
	}
}
