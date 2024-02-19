package com.kbtg.bootcamp.posttest.lottery.model.request;

import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateRequest {

    @NotNull
    @Size(
            min = LotteryModuleConstant.TICKET_LENGTH,
            max = LotteryModuleConstant.TICKET_LENGTH,
            message = LotteryModuleConstant.MSG_INVALID_TICKET_ID
    )
    @Pattern(regexp = "\\d+", message = LotteryModuleConstant.MSG_DIGIT_ONLY)
    private String ticket;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Positive
    private Integer amount;


}
