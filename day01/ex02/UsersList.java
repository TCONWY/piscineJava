public interface UsersList {
    public void addUser(User us);
    public User retId(int id) throws UserNotFoundException;
    public User retIndex(int in) throws UserNotFoundException;
    public int numUser();
}
