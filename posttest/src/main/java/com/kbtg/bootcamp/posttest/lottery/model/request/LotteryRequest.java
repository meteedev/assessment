package com.kbtg.bootcamp.posttest.lottery.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LotteryRequest {

    @NotNull
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Invalid characters in name")
    private String ticket;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Positive
    private Double amount;


}
