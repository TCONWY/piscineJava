import java.util.UUID;

public class Program {

    public static void main(String[] args) {
        int max_usrs = (int)(Math.random() * 10 + 15);
        UUID[] tt = new UUID[max_usrs];
        System.out.println("Generating " + max_usrs + " users.");
        TransactionsLinkedList transactions = new TransactionsLinkedList();
        User sender = new User("Pupa", 123);
        User recipient = new User("Lupa", 0);

        for (int i = 0; i < max_usrs; i++) {
            Transaction tmp;
            if (i % 2 == 0) {
                tmp = new Transaction(sender, recipient, Сategory.DEBIT, 123);
            }	else {
                tmp = new Transaction(recipient, sender, Сategory.CREDIT, -123);
            }
            tt[i] = tmp.getUUID();
            transactions.addTran(tmp);
        }

        for (Transaction t : transactions.tranArray())	{
            System.out.println(t.getUUID());
        }
        System.out.println("DELETED");
        System.out.println(tt[5]);
        try {
            transactions.delTran(tt[5]);
        }
        catch (TransactionNotFoundException e){
            System.out.println(e.toString());
        }
        System.out.println("DELETED");
        for (Transaction a : transactions.tranArray())	{
            System.out.println(a.getUUID());
        }
    }
}
