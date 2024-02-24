package com.kbtg.bootcamp.posttest.lottery.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name="user_ticket")
@NoArgsConstructor
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "ticket")
    private String ticket;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Double price;

    @Column(name = "total_bill")
    private Double totalBill;


}
