package com.kbtg.bootcamp.posttest.lottery.model.dto;

import lombok.Data;

@Data
public class UserTicketDto {

    private String userId;
    private String ticket;
    private Integer amount;

}
