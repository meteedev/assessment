package com.kbtg.bootcamp.posttest.lottery.model.request;

import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
	@Positive
	@Pattern(regexp = "\\d+", message = LotteryModuleConstant.MSG_DIGIT_ONLY)
	private Double price;

	@NotNull
	@Positive
	@Pattern(regexp = "\\d+", message = LotteryModuleConstant.MSG_DIGIT_ONLY)
	private Integer amount;


}
