import java.util.UUID;

public class TransactionsService {

    UsersList usrList = new UsersArrayList();
    int size = 0;

    public void addUser(User usr){
        usrList.addUser(usr);
        size++;
    }

    public int getBalance(int id){
        User usr = null;
        try{
            usr = usrList.retId(id);
        } catch (UserNotFoundException e){
            System.out.println(e.toString());
            return 0;
        }
        return usr.getBalance();
    }

    public void executeTransaction(int idR, int idS, int sum){
        User usrR, usrS = null;
        try{
            usrS = usrList.retId(idS);
            usrR = usrList.retId(idR);
        } catch (UserNotFoundException e){
            System.out.println(e.toString());
            return;
        }
        if (usrS.getBalance() <= sum){
            throw new IllegalTransactionException();
        }
        if(sum > 0) {
            Transaction transaction = new Transaction(usrS, usrR, Category.CREDIT, -sum);
            Transaction transaction1 = new Transaction(usrS, usrR, Category.DEBIT, sum);
            transaction1.set_UUID(transaction.getUUID());
            usrS.addTran(transaction1);
            usrR.addTran(transaction);
        } else {
            Transaction transaction = new Transaction(usrS, usrR, Category.CREDIT, -sum);
            Transaction transaction1 = new Transaction(usrS, usrR, Category.DEBIT, sum);
            transaction1.set_UUID(transaction.getUUID());
            usrS.addTran(transaction);
            usrR.addTran(transaction1);
        }
    }

    public Transaction[] getTrArr(int id){
        User usr = null;
        try{
            usr = usrList.retId(id);
        } catch (UserNotFoundException e){
            System.out.println(e.toString());
            return null;
        }
        return usr.getLst().tranArray();
    }

    public void removeTransaction(UUID idTransaction, int idUser){
        usrList.retId(idUser).getLst().delTran(idTransaction);
    }


    public Transaction[] getFailTrArr(){
        TransactionsList notValid = new TransactionsLinkedList();

        for (int i = 0; i < usrList.getCount(); i++) {
            System.out.println(usrList.getCount() + " getCount ");
            Transaction[] transactions = this.usrList.retIndex(i).getLst().tranArray();
            for (Transaction t : transactions) {
                boolean valid = false;
                for (Transaction t2 : t.getRecipient().getLst().tranArray()) {
                    if (t.getUUID() == t2.getUUID()) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    notValid.addTran(t);
                }
            }
        }
        return notValid.tranArray();
    }

}