package com.kbtg.bootcamp.posttest.lottery.model.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PurchaseRequest {
    @PositiveOrZero(message = "amount must not be less than 0")
    private Integer amount;
}
