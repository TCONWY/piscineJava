public class Program {
    public static void main(String[] args) {
        User user1 = new User(1, "John", 10000);
        User user2 = new User(2, "John", 10000);
        User user3 = new User(3, "Mike", 10000);
        System.out.printf("User1 id - %d, name - %s, balance - %d\n",
                user1.getId(), user1.getName(), user1.getBalance());
        System.out.printf("User2 id - %d, name - %s, balance - %d\n",
                user2.getId(), user2.getName(), user2.getBalance());
        System.out.printf("User3 id - %d, name - %s, balance - %d\n",
                user3.getId(), user3.getName(), user3.getBalance());
        Transaction transaction2 = new Transaction(user1, user2,
                Сategory.CREDIT, -100);
        System.out.println(transaction2.getUUID());
        System.out.println(transaction2.getRecipient().getId());
        System.out.println(transaction2.getSender().getId());
        System.out.println(transaction2.getCategory());
        System.out.println(transaction2.getSum());
        Transaction transaction3 = new Transaction(user1, user2,
                Сategory.DEBIT, 100);
        if (transaction3.getSum() == 0)
            return;
        System.out.println(transaction3.getUUID());
        System.out.println(transaction3.getRecipient().getId());
        System.out.println(transaction3.getSender().getId());
        System.out.println(transaction3.getCategory());
        System.out.println(transaction3.getSum());
    }
}
