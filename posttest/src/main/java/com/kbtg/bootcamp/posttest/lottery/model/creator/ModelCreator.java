package com.kbtg.bootcamp.posttest.lottery.model.creator;

import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketSummary;
import com.kbtg.bootcamp.posttest.lottery.model.response.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ModelCreator {

    public UserTicketDto createUserTicketDto(String userId ,String ticketId){
        UserTicketDto userTicketDto =  new UserTicketDto();
        userTicketDto.setUserId(userId);
        userTicketDto.setTicket(ticketId);
        return userTicketDto;
    }



    public GetLotteryResponse createGetLotteryResponse(List<String> tickets) {
        return new GetLotteryResponse(tickets);
    }


    public ViewLotteryPurchase createViewLotteryPurchase(List<UserTicketSummary> userTicketSummaryList) {

        List<String> tickets = new ArrayList<>();
        double totalBilling = 0.0;
        int totalAmount = 0;

        for(UserTicketSummary userTicketSummary:userTicketSummaryList){
            tickets.add(userTicketSummary.getTicket());
            totalBilling += userTicketSummary.getTotalTicketBillPrice();
            totalAmount += userTicketSummary.getTotalTicketAmount();
        }

        return new ViewLotteryPurchase(
                tickets,
                totalAmount,
                totalBilling
        );
    }

    public SellBackLotteryResponse createSellBackLotteryResponse(String ticket) {
       return new SellBackLotteryResponse(ticket);
    }

}
