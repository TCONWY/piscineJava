public class User {
    private int id;
    private String name;
    private int balance;

    public User(String name, Integer balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
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

    public void setName(String name){
        this.name = name;
    }
}
