package com.kbtg.bootcamp.posttest.lottery.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserTicketSummary {
    private String ticket;
    private String userId;
    private Long totalTicketAmount;
    private Double totalTicketBillPrice;
}



