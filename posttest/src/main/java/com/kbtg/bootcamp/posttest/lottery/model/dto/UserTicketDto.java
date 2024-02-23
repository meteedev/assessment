package com.kbtg.bootcamp.posttest.lottery.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTicketDto {
    private String userId;
    private String ticket;
    private Integer amount;
}
