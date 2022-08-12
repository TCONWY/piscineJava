package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.Objects;

public class Chatroom {
	private Long id;
	private ArrayList<Message> roomMessages;
	private User owner;
	private String name;

	public Chatroom(Long id, String name, User owner, ArrayList<Message> roomMessages){
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.roomMessages = roomMessages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Message> getRoomMessages() {
		return roomMessages;
	}

	public void setRoomMessages(ArrayList<Message> roomMessages) {
		this.roomMessages = roomMessages;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Chatroom chatroom = (Chatroom) obj;
		return id.equals(chatroom.getId())  &&
				name.equals(chatroom.getName()) &&
				owner.equals(chatroom.getOwner()) &&
				roomMessages.equals(chatroom.getRoomMessages());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, owner, roomMessages);
	}

	@Override
	public String toString() {
		return "Chatroom{" +
						"id=" + id +
						", name=" + name +
						", owner=" + owner +
						", roomMessages=" + roomMessages +
						'}';
	}
}

