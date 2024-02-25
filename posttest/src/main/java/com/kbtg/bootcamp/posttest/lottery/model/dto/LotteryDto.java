package com.kbtg.bootcamp.posttest.lottery.model.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
public class LotteryDto {
	private String ticket;
	private Integer amount;
	private Double price;
}
