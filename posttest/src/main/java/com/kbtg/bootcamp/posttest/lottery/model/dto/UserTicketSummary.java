package com.kbtg.bootcamp.posttest.lottery.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserTicketSummary {
    private String ticket;
    private String userId;
    private Integer totalTicketAmount;
    private Double totalTicketBillPrice;
}



