package edu.school21.ORM.classes;

import edu.school21.ORM.annotations.OrmColumn;
import edu.school21.ORM.annotations.OrmColumnId;
import edu.school21.ORM.annotations.OrmEntity;

@OrmEntity(table = "User")
public class User {
	@OrmColumnId
	private Long id;
	@OrmColumn(name = "first_name", length = 10)
	private String firstName;
	@OrmColumn(name = "lastName", length = 10)
	private String lastName;
	@OrmColumn(name = "age")
	private Integer age;

	public User() {
	}

	public User(Long id, String firstName, String lastName, Integer age) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", age=" + age +
				'}';
	}
}
