public class User {
    private int id;
    private String name;
    private int balance;

    public User(int ID, String name, int balance) {
        this.id = ID;
        setName(name);
        setBalance(balance);
    }

    public int getId(){
        return id;
    }

    public int getBalance(){
        return balance;
    }

    public String getName(){
        return name;
    }

    public void setBalance(int balance){
        this.balance = balance;
    }

    public void setName(String name) {
        this.name = name;
    }
}
