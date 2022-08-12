import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("aaa", 928374);
        User user2 = new User("bbb", 629745);
        User user3 = new User("ccc", 972634);
        TransactionsService service = new TransactionsService();
        service.addUser(user1);
        service.addUser(user2);
        service.addUser(user3);
        System.out.println(user1.getBalance() + "bal1");
        System.out.println(user2.getBalance() + "bal2\n");
        service.executeTransaction(user1.getId(), user2.getId(), 1000);
        System.out.println(user1.getBalance() + "bal1");
        System.out.println(user2.getBalance() + "bal2");
        service.executeTransaction(user1.getId(), user2.getId(), 1001);
        System.out.println(user1.getName() + " bal1");
        System.out.println(user2.getName() + " bal2");
        System.out.println("------------------------");
        System.out.println(service);
        System.out.println("------------------------");
        System.out.println("choose and del transaction");
        Transaction tmp = user1.getLst().tranArray()[0];
        System.out.println(tmp);
        System.out.println("------------------------");
        System.out.println("service now");
        service.removeTransaction(user1.getLst().tranArray()[0].getUUID(), user1.getId());
        System.out.println(service);
        System.out.println("------------------------");
        System.out.println("not valid without pair");
        for (Transaction t : service.getFailTrArr()) {
            System.out.println(t);
            service.removeTransaction(t.getUUID(), t.getSender().getId());
        }
        System.out.println("------------------------");
        System.out.println("del not valid transactions in service");
        System.out.println(service);
        System.out.println("------------------------");
        System.out.println("calculate");
        service.getBalance(1);
        System.out.println(service);
        System.out.println("------------------------");
        Transaction[] tr = service.getTrArr(user1.getId());
        for (Transaction t : tr) {
            System.out.println(t.getUUID());
        }
        service.executeTransaction(user1.getId(), user2.getId(), 1000000);
    }
}
