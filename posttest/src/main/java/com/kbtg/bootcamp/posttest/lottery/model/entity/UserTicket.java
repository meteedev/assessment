package com.kbtg.bootcamp.posttest.lottery.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="user_ticket")
@NoArgsConstructor
@AllArgsConstructor
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
