package com.kbtg.bootcamp.posttest.lottery.model.dto;

;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class UserTicketSummary {
    private String ticket;
    private String userId;
    private Long totalTicketAmount;
    private Double totalTicketBillPrice;
}



