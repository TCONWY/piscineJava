package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.Objects;

public class User {
	private Long id;
	private String login;
	private String password;
	private ArrayList<Chatroom> chats;
	private ArrayList<Chatroom> createChats;

	public User(Long userId, String password, ArrayList<Chatroom> createChats, ArrayList<Chatroom> chats){
		this.id = userId;
		this.password = password;
		this.createChats = createChats;
		this.chats = chats;
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

	public ArrayList<Chatroom> getChats() {
		return chats;
	}

	public ArrayList<Chatroom> getCreateChats() {
		return createChats;
	}

	public void setUserId(Long id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setChats(ArrayList<Chatroom> chats) {
		this.chats = chats;
	}

	public void setCreateChats(ArrayList<Chatroom> createChats) {
		this.createChats = createChats;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if(obj == null || getClass() == obj.getClass())
			return false;
		User user = (User) obj;
		return id.equals(user.getId()) &&
				login.equals(user.getLogin()) &&
				password.equals(user.getPassword()) &&
				createChats.equals(user.getCreateChats()) &&
				chats.equals(user.getCreateChats());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, createChats, chats);
	}

	@Override
	public String toString() {
		return "User{" +
					"userId=" + id +
					", login=" + login +
					", password" + password +
					", createChats" + createChats +
					", chats" + chats +
					'}';
	}
}
