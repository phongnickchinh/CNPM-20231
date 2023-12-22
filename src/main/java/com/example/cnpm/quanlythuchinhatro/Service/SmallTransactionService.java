package com.example.cnpm.quanlythuchinhatro.Service;
import com.example.cnpm.quanlythuchinhatro.Repository.SmallTransactionRepository;
import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SmallTransactionService {

    @Autowired
    private SmallTransactionRepository repository;

    public SmallTransaction saveTransaction(SmallTransaction transaction) {

        return repository.save(transaction);
    }

    public List<SmallTransaction> getAllTransactions() {

        return repository.findAll();
    }

    public SmallTransaction updateTransaction(Integer id, SmallTransaction transactionDetails) {
        SmallTransaction transaction = repository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found with id : " + id));
        transaction.setRoomId(transactionDetails.getRoomId());
        transaction.setUserId(transactionDetails.getUserId());
        transaction.setItemName(transactionDetails.getItemName());
        transaction.setTransactionTime(transactionDetails.getTransactionTime());
        transaction.setPrice(transactionDetails.getPrice());
        transaction.setNote(transactionDetails.getNote());
        return repository.save(transaction);
    }

    public void deleteTransaction(Integer id) {
        repository.deleteById(id);
    }
}
