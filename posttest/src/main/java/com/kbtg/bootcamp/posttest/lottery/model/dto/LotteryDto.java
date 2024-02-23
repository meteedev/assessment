package com.kbtg.bootcamp.posttest.lottery.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LotteryDto {
    private String ticket;
    private Integer amount;
    private Double price;
}
