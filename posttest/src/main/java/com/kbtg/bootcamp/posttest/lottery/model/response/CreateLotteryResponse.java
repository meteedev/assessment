package com.kbtg.bootcamp.posttest.lottery.model.response;

public record CreateLotteryResponse(
        Integer no,
        String ticket,
        Integer amount,
        Double price
) {

}
