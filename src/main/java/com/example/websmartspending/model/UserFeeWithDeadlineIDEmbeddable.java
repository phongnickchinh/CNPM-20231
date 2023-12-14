package com.example.websmartspending.model;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
//tạo khóa chính cho bảng User_FeeWithDeadline
public class UserFeeWithDeadlineIDEmbeddable implements Serializable {
    private Integer userId;
    private Long idFee;
    public UserFeeWithDeadlineIDEmbeddable(Integer userId, Long idFee) {
        this.userId = userId;
        this.idFee = idFee;
    }


    
}
