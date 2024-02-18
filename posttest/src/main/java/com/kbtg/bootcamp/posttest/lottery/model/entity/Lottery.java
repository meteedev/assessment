package com.kbtg.bootcamp.posttest.lottery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "lottery")
public class Lottery {

    @Id
    @Column(name="ticket")
    private String ticket;

    @Column(name="amount")
    private Integer amount;

    @Column(name="price")
    private Double price;

}
