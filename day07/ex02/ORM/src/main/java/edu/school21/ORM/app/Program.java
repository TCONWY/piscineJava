package edu.school21.ORM.app;


import edu.school21.ORM.classes.Car;
import edu.school21.ORM.classes.User;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class Program {
	private static final String PACKAGE_NAME = "edu.school21.ORM.classes.";
	private EmbeddedDatabase dataSourse;
	private OrmManager manager;

	public static void main(String[] args) throws NoSuchMethodException {
		Program program = new Program();
		program.dataSourse = new EmbeddedDatabaseBuilder().build();
		program.manager = new OrmManager(PACKAGE_NAME, program.dataSourse);
		System.out.println("qwe");
		program.manager.init();
		System.out.println("qwe");
		program.testSave();
		program.testFind();
		program.testFind();
		program.testUpdate();
		program.dataSourse.shutdown();
	}

	private void testSave() throws NoSuchMethodException {
		System.out.println("\n\n\n--SAVE TEST-");
		User user = new User(1L, "Ivan", "Ivanov", 35);
		User user2 = new User(2L, "Peter", "Petrov", 40);
		manager.save(user);
		manager.save(user2);

		Car car = new Car("Audi", "red", 1L);
		Car car2 = new Car("Mers", "black", 2L);
		manager.save(car);
		manager.save(car2);
	}

	private void testFind() {
		System.out.println("\n\n\n--FIND TEST--");
		User user;
		if ((user = manager.findById(2L, User.class)) != null) {
			System.out.println(user);
		}
		Car car;
	 	if ((car = manager.findById(1L, Car.class)) != null) {
			System.out.println(car);
		}
	}

	private void testUpdate() {
		System.out.println("\n\n\n--UPDATE TEST--");
		User user;
		if ((user = manager.findById(1L, User.class)) != null) {
			System.out.println("Before update:" + user);
		}
		user = new User(1L, "Peter", "Petrov", 40);
		manager.update(user);

		if ((user = manager.findById(1L, User.class)) != null) {
			System.out.println("After update:" + user);
		}

		Car car;
		if ((car = manager.findById(2L, Car.class)) != null) {
			System.out.println("Before update:" + car);
		}
		car = new Car("BMW", "green", 3L);
		manager.update(car);

		if ((car = manager.findById(2L, Car.class)) != null) {
			System.out.println("After update:" + car);
		}
	}
}
