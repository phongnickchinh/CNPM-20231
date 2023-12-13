package com.example.websmartspending.model;
import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SmallTransaction")
public class SmallTransaction {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "IDSmallTransaction")
        private Long idSmallTransaction;
    
        @ManyToOne
        @JoinColumn(name = "IDUser", insertable = false, updatable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "IDRoom", insertable = false, updatable = false)
        private Room room;

        @Column(name = "ItemName")
        private String itemName;

        @Column(name = "Price")
        private Long price;
    
        @Column(name = "TransactionTime")
        private LocalDateTime transactionTime;
    
        @Column(name = "Note")
        private String note;
    
        // Constructors
        public SmallTransaction() {
        }
        public SmallTransaction(User user, Room room, Long price, LocalDateTime transactionTime, String note) {
            this.user = user;
            this.room = room;
            this.price = price;
            this.transactionTime = transactionTime;
            this.note = note;
        }
    
}
