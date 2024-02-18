package com.kbtg.bootcamp.posttest.lottery.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateRequest {

    @NotNull
    @Size(min = 6, max = 6, message = "Lottery Length must be 6 characters")
    @Pattern(regexp = "\\d+", message = "Should contain only digits")
    private String ticket;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Positive
    private Integer amount;


}
