package com.kbtg.bootcamp.posttest.lottery.model.response;

public record PurchaseLotteryResponse(
        Integer id,
        String ticket,
        Integer amount,
        Double price

) {

}
