package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.Objects;

public class User {
	private Long id;
	private String login;
	private String password;
	private ArrayList<Chatroom> createRooms;
	private ArrayList<Chatroom> rooms;

	public User(Long id, String login, String password, ArrayList<Chatroom> createRooms, ArrayList<Chatroom> rooms) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.createRooms = createRooms;
		this.rooms = rooms;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<Chatroom> getCreateRooms() {
		return createRooms;
	}

	public ArrayList<Chatroom> getRooms() {
		return rooms;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		User user = (User)obj;
		return id.equals(user.getId()) && login.equals(user.getLogin()) && password.equals(user.getPassword())
				&& createRooms.equals(user.getCreateRooms()) && rooms.equals(user.getRooms());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, createRooms, rooms);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", createdRooms=" + createRooms +
				", rooms=" + rooms +
				"}";
	}
}
