package com.example.cnpm.quanlythuchinhatro.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;

@Repository
public interface SmallTransactionRepository extends JpaRepository<SmallTransaction, Integer> {

}
