package com.kbtg.bootcamp.posttest.lottery.model.creator;

import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketSummary;
import com.kbtg.bootcamp.posttest.lottery.model.response.GetLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.model.response.SellBackLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.model.response.ViewLotteryPurchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class ModelCreatorTest {

    private ModelCreator modelCreator;
    @BeforeEach
    void setUp(){
        modelCreator = new ModelCreator();
    }

    @Test
    void createUserTicketDto() {
      String userId = "11111111111";
      String ticketId = "123456";
      UserTicketDto userTicketDto = modelCreator.createUserTicketDto(userId,ticketId);

      assertEquals(userId,userTicketDto.getUserId());
      assertEquals(ticketId,userTicketDto.getTicket());

    }

    @Test
    void createGetLotteryResponse() {
        List<String> tickets = Arrays.asList("11111","22222");
        GetLotteryResponse getLotteryResponse = modelCreator.createGetLotteryResponse(tickets);
        assertEquals(tickets.size(),getLotteryResponse.tickets().size());
    }

    @Test
    void createViewLotteryPurchase() {

        UserTicketSummary  userTicketSummary1 = new UserTicketSummary("T1","U1",2L,160.0);
        UserTicketSummary  userTicketSummary2 = new UserTicketSummary("T2","U2",1L,80.0);
        List<UserTicketSummary> userTicketSummaries = Arrays.asList(userTicketSummary1,userTicketSummary2);

        ViewLotteryPurchase viewLotteryPurchase = modelCreator.createViewLotteryPurchase(userTicketSummaries);
        assertEquals(3,viewLotteryPurchase.count());
        assertEquals(240.0,viewLotteryPurchase.cost());

    }

    @Test
    void createSellBackLotteryResponse() {
        String ticket = "T1";
        SellBackLotteryResponse sellBackLotteryResponse = modelCreator.createSellBackLotteryResponse(ticket);
        assertEquals(ticket,sellBackLotteryResponse.ticket());
    }
}