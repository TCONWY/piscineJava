import java.util.UUID;

public interface TransactionsList {
    public void addTran(Transaction tr);
    public void delTran(UUID id);
    public Transaction[] tranArray();
}
