package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
	private Long id;
	private User author;
	private Chatroom room;
	private String text;
	private LocalDateTime messageTime;

	public Message(Long id, User author, Chatroom room, String text, LocalDateTime messageTime){
		this.id = id;
		this.author = author;
		this.room = room;
		this.text = text;
		this.messageTime = messageTime;
	}

	public Long getId() {
		return id;
	}

	public User getAuthor() {
		return author;
	}

	public Chatroom getRoom() {
		return room;
	}

	public String getText() {
		return text;
	}

	public LocalDateTime getMessageTime() {
		return messageTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void setRoom(Chatroom room) {
		this.room = room;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setMessageTime(LocalDateTime messageTime) {
		this.messageTime = messageTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Message message = (Message) obj;
		return id.equals(message.getId()) &&
				author.equals(message.getAuthor()) &&
				room.equals(message.getRoom()) &&
				text.equals(message.getText()) &&
				messageTime.equals(message.getMessageTime());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, author, room, text, messageTime);
	}

	@Override
	public String toString() {
		return "Message{" +
				"messageId=" + id +
				", author=" + author +
				", room=" + room +
				", text=" + text +
				", messageTime=" + messageTime +
				'}';
	}
}
