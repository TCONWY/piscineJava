import java.util.UUID;

enum Сategory{CREDIT, DEBIT};

public class Transaction {
    private UUID _UUID;
    private User recipient;
    private User sender;
    private Сategory Category;
    private int sum;

    public Transaction(User sender, User recipient, Сategory сategory, int sum) {
        this._UUID = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.Category = сategory;
        if ((сategory == Category.CREDIT && sum > 0) || сategory == Category.DEBIT && sum < 0) {
            System.out.println("Transaction failed");
            sum = 0;
        }if (sender.getBalance() < 0) {
            System.out.println("Transaction failed");
            sum = 0;
        }
        this.sum = sum;
        if (Category == Category.CREDIT) {
            sender.setBalance(sender.getBalance() + sum);
        } else {
            recipient.setBalance(recipient.getBalance() + sum);
        }
    }

    public UUID getUUID() {
        return _UUID;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Сategory getCategory() {
        return this.Category;
    }

    public int getSum() {
        return sum;
    }
}