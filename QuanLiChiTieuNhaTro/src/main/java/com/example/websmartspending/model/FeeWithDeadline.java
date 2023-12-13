package com.example.websmartspending.model;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "FeeWithDeadline")
public class FeeWithDeadline {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "IDFee")
        private Long idFee;
        
        @ManyToOne
        @JoinColumn(name ="IDRoom")
        private Room room;

        @Column(name = "Name")
        private String name;

        @Column(name = "Money")
        private BigDecimal money;

        @Column(name = "Status")
        private Integer status;

        @Column(name = "Deadline")
        private Date deadline;

        @Column(name = "Type")
        private Integer type; // 1: phí định kì, 2: phí phát sinh

        @OneToMany(mappedBy = "feeWithDeadline", cascade = CascadeType.ALL)
        private List<UserFeeWithDeadline> userFeeWithDeadlines;
    
        public FeeWithDeadline() {
        }
    
        public FeeWithDeadline(Room room, String name, BigDecimal money, Integer status, Date deadline, Integer type) {
            this.room = room;
            this.name = name;
            this.money = money;
            this.status = status;
            this.deadline = deadline;
            this.type = type;
        }
    
}
