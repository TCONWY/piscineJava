package school21.spring.service.models;

public class User {
    private Long id;
    private String Email;

    public User(Long id, String email) {
        this.id = id;
        Email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return Email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Email='" + Email + '\'' +
                '}';
    }
}
