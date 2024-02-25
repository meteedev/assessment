package com.kbtg.bootcamp.posttest.lottery.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class UserTicketDto {
	private String userId;
	private String ticket;
	private Integer amount;
}
