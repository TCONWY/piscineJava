package edu.school21.chat.models;

import java.time.LocalDateTime;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime time;

    public Message(Long id, User author, Chatroom room, String text, LocalDateTime time) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || this.getClass() != obj.getClass())
            return false;
        Message message = (Message) obj;
        return id.equals(message.getId())&& author.equals(message.getAuthor()) && room.equals(message.getRoom())
                && text.equals(message.getText()) && time.equals(message.getTime());
    }

    @Override
    public String toString() {
        return "Message{" +
                "\nid=" + id +
                ",\nauthor=" + author +
                ",\nchatroom=" + room +
                ",\ntext='" + text + '\'' +
                ",\nmessageDateTime=" + time +
                "\n}";
    }
}
