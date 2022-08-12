package edu.school21.sockets.models;

public class User {
    private String name;
    private Long id;

    public User(Long id ,String name) {
        this.name = name;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Server{" +
                "name=" + name +
                ", age='" + id + '\'' +
                '}';
    }
}
