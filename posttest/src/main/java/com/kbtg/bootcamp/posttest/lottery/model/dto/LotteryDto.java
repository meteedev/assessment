package com.kbtg.bootcamp.posttest.lottery.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LotteryDto {
    private String ticket;
    private Integer amount;
    private Double price;

}
