package com.kbtg.bootcamp.posttest.lottery.model.response;

import java.util.List;

public record ViewLotteryPurchase (
        List<String> tickets,
        Integer count,
        Double cost
){
}
