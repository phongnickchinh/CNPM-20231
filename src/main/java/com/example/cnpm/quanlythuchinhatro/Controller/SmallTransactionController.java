    package com.example.cnpm.quanlythuchinhatro.Controller;
    import com.example.cnpm.quanlythuchinhatro.Service.SmallTransactionService;
    import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import java.util.List;

    @RestController
    @RequestMapping("/SmallTransaction")
    public class SmallTransactionController {

        @Autowired
        private SmallTransactionService service;

        @PostMapping("/add")
        public SmallTransaction createTransaction(@RequestBody SmallTransaction transaction) {
            return service.saveTransaction(transaction);
        }

        @GetMapping("/show")
        public List<SmallTransaction> getAllTransactions() {
            return service.getAllTransactions();
        }

        @PutMapping("/update/{id}")
        public SmallTransaction updateTransaction(@PathVariable Integer id, @RequestBody SmallTransaction transactionDetails) {
            return service.updateTransaction(id, transactionDetails);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<?> deleteTransaction(@PathVariable Integer id) {
            service.deleteTransaction(id);
            return ResponseEntity.ok().build();
        }
    }
