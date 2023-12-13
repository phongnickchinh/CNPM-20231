package com.example.websmartspending.model;
import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "User_FeeWithDeadline")
public class UserFeeWithDeadline {

    @EmbeddedId
    private UserFeeWithDeadlineIDEmbeddable id;

    @ManyToOne
    @JoinColumn(name = "IDUser", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "IDFee", insertable = false, updatable = false)
    private FeeWithDeadline feeWithDeadline;

    @Column(name = "Status")
    private Integer status;

    // Constructors
    public UserFeeWithDeadline() {
    }
    public UserFeeWithDeadline(User user, FeeWithDeadline feeWithDeadline, Integer status) {
        this.user = user;
        this.feeWithDeadline = feeWithDeadline;
        this.status = status;
        this.id = new UserFeeWithDeadlineIDEmbeddable(user.getIdUser(), feeWithDeadline.getIdFee());
    }
}