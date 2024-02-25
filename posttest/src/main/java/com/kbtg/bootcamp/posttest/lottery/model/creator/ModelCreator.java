package com.kbtg.bootcamp.posttest.lottery.model.creator;

import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketSummary;
import com.kbtg.bootcamp.posttest.lottery.model.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.model.entity.UserTicket;
import com.kbtg.bootcamp.posttest.lottery.model.request.CreateRequest;
import com.kbtg.bootcamp.posttest.lottery.model.response.*;
import com.sun.source.doctree.BlockTagTree;
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

    public LotteryDto mapCreateRequestToLotteryDTO(CreateRequest createRequest) {
        if ( createRequest == null ) {
            return null;
        }

        String ticket = null;
        Integer amount = null;
        Double price = null;

        ticket = createRequest.getTicket();
        amount = createRequest.getAmount();
        price = createRequest.getPrice();

        return new LotteryDto( ticket, amount, price );

    }


    public Lottery mapLotteryDTOToLotteryEntity(LotteryDto lotteryDto) {
        if ( lotteryDto == null ) {
            return null;
        }

        Lottery lottery = new Lottery();

        lottery.setTicket( lotteryDto.getTicket() );
        lottery.setAmount( lotteryDto.getAmount() );
        lottery.setPrice( lotteryDto.getPrice() );

        return lottery;
    }


    public CreateLotteryResponse mapLotteryEntityToCreateLotteryResponse(Lottery lottery) {
        if ( lottery == null ) {
            return null;
        }

        String ticket = null;

        ticket = lottery.getTicket();

        return new CreateLotteryResponse( ticket );

    }


    public UserTicket mapUserTicketDTOToUserTicketEntity(UserTicketDto userTicketDto) {
        if ( userTicketDto == null ) {
            return null;
        }

        UserTicket userTicket = new UserTicket();

        userTicket.setUserId( userTicketDto.getUserId() );
        userTicket.setTicket( userTicketDto.getTicket() );

        return userTicket;
    }


    public PurchaseLotteryResponse mapUserTicketToPurchaseLotteryResponse(UserTicket userTicket) {
        if ( userTicket == null ) {
            return null;
        }

        String id = null;

        if ( userTicket.getNo() != null ) {
            id = String.valueOf( userTicket.getNo() );
        }

        return new PurchaseLotteryResponse( id );

    }

}
