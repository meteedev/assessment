package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

class LotteryControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LotteryService lotteryService;

    @Autowired
    private LotteryDto lotteryDto;

    @BeforeEach
    void setUp() {
        //lotteryDto = LotteryDto.builder().ticket("123456").price(80.8).amount(100).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createLottery() {
    }

    @Test
    void getAllLottery() {
    }

    @Test
    void purchaseLottery() {
    }

    @Test
    void viewLotteryPurchaseByUser() {
    }

    @Test
    void sellBackLottery() {
    }
}