package com.kbtg.bootcamp.posttest.lottery.model.response;

public record CreateLotteryResponse(
        String ticket,
        Integer amount,
        Double price
) {

}
