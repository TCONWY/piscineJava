import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    LinkedList lst = null;

    private  int len = 0;

    @Override
    public void addTran(Transaction tr) {
        LinkedList newTransactionNode = new LinkedList(tr);

        if (lst != null) {
            lst.setPrev(newTransactionNode);
        }
        newTransactionNode.setPrev(null);
        newTransactionNode.setNext(lst);
        lst = newTransactionNode;
        len++;
    }

    @Override
    public void delTran(UUID id) {
        LinkedList lst2 = lst;
        while(lst2 != null){
            if (lst2.getTr().getUUID().equals(id)){
                if(lst2.getPrev() != null) {
                    lst2.getPrev().setNext(lst2.getNext());
                }
                lst2.getNext().setPrev(lst2.getPrev());
                return;
            }
            lst2 = lst2.getNext();
        }
        throw new TransactionNotFoundException();
    }

    @Override
    public Transaction[] tranArray() {
        LinkedList lst = this.lst;
        int i = 0;
        while (lst != null){
            i++;
            lst = lst.getNext();
        }
        Transaction[] trArr = new Transaction[i];
        lst = this.lst;
        i = 0;
        while (lst != null){
            trArr[i++] = lst.getTr();
            lst = lst.getNext();
        }
        return trArr;
    }
}

class LinkedList{
    LinkedList next = null;
    Transaction tr = null;
    LinkedList prev = null;


    public LinkedList(Transaction transaction) {
        this.tr = transaction;
    }

    public  LinkedList(LinkedList next, LinkedList previous, Transaction data) {
        this.next = next;
        this.prev = previous;
        this.tr = data;
    }

    public LinkedList getNext() {
        return next;
    }

    public Transaction getTr() {
        return tr;
    }

    public LinkedList getPrev() {
        return prev;
    }

    public void setNext(LinkedList next) {
        this.next = next;
    }

    public void setTr(Transaction tr) {
        this.tr = tr;
    }

    public void setPrev(LinkedList prev) {
        this.prev = prev;
    }
}
