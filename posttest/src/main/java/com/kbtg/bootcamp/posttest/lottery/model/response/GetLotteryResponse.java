package com.kbtg.bootcamp.posttest.lottery.model.response;

import lombok.Data;

import java.util.List;


public record GetLotteryResponse(List<String> tickets) {
}