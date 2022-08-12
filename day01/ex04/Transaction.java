import java.util.UUID;

enum Category{CREDIT, DEBIT};

public class Transaction {
    private UUID _UUID;
    private User Recipient;
    private User Sender;
    private Category Category;
    private int sum;

    public Transaction(User sender, User recipient, Category сategory, int sum) {
        this._UUID = UUID.randomUUID();
        this.Recipient = recipient;
        this.Sender = sender;
        this.Category = сategory;
        if ((сategory == Category.CREDIT && sum > 0) || сategory == Category.DEBIT && sum < 0) {
            System.out.println("Transaction failed");
            sum = 0;
        }if (recipient.getBalance() < 0) {
            System.out.println("Transaction failed");
            sum = 0;
        }
        this.sum = sum;
        if (Category == Category.CREDIT) {
            Recipient.setBalance(Recipient.getBalance() + sum);
        } else {
            Sender.setBalance(Sender.getBalance() + sum);
        }
    }


    public void set_UUID(UUID _UUID) {
        this._UUID = _UUID;
    }

    public void setRecipient(User recipient) {
        Recipient = recipient;
    }

    public void setSender(User sender) {
        Sender = sender;
    }

    public void setCategory(Category category) {
        Category = category;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public UUID getUUID() {
        return _UUID;
    }

    public User getRecipient() {
        return Recipient;
    }

    public User getSender() {
        return Sender;
    }

    public Category getCategory() {
        return this.Category;
    }

    public int getSum() {
        return sum;
    }
}