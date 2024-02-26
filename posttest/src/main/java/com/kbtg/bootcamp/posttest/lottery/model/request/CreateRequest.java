package com.kbtg.bootcamp.posttest.lottery.model.request;

import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CreateRequest {

	static final int TICKET_LENGTH  = 6;

	@NotNull
	@Size(
			min = CreateRequest.TICKET_LENGTH,
			max =  CreateRequest.TICKET_LENGTH,
			message = LotteryModuleConstant.MSG_INVALID_TICKET_FORMAT
	)
	@Pattern(regexp = "\\d+", message = LotteryModuleConstant.MSG_DIGIT_ONLY)
	private String ticket;

	@NotNull
	@Digits(integer = Integer.MAX_VALUE, fraction = Integer.MAX_VALUE, message = LotteryModuleConstant.MSG_DIGIT_ONLY)
	@Positive
	private Double price;

	@NotNull
	@Digits(integer = Integer.MAX_VALUE, fraction = 0, message = LotteryModuleConstant.MSG_DIGIT_ONLY)
	@Positive
	private Integer amount;


}
