package com.kbtg.bootcamp.posttest.lottery.model.creator;

import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketSummary;
import com.kbtg.bootcamp.posttest.lottery.model.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.model.entity.UserTicket;
import com.kbtg.bootcamp.posttest.lottery.model.request.CreateRequest;
import com.kbtg.bootcamp.posttest.lottery.model.request.PurchaseRequest;
import com.kbtg.bootcamp.posttest.lottery.model.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelCreator {

    public LotteryDto createLotteryDto(CreateRequest createRequest){
        LotteryDto lotteryDto =  new LotteryDto();
        BeanUtils.copyProperties(createRequest,lotteryDto);
        return lotteryDto;
    }

    public UserTicketDto createUserTicketDto(String userId ,String ticketId,PurchaseRequest purchaseRequest){
        UserTicketDto userTicketDto =  new UserTicketDto();
        BeanUtils.copyProperties(purchaseRequest,userTicketDto);
        userTicketDto.setUserId(userId);
        userTicketDto.setTicket(ticketId);
        return userTicketDto;
    }

    public UserTicketDto createUserTicketDto(String userId ,String ticketId){
        UserTicketDto userTicketDto =  new UserTicketDto();
        userTicketDto.setUserId(userId);
        userTicketDto.setTicket(ticketId);
        return userTicketDto;
    }



    public Lottery createLotteryEntity(LotteryDto lotteryDto){
        Lottery lottery =  new Lottery();
        BeanUtils.copyProperties(lotteryDto,lottery);
        return lottery;
    }

    public UserTicket createUserTicketEntity(UserTicketDto userTicketDto){
        UserTicket userTicket =  new UserTicket();
        BeanUtils.copyProperties(userTicketDto,userTicket);
        return userTicket;
    }



    public CreateLotteryResponse createCreateLotteryResponse(Lottery lottery){
        return new CreateLotteryResponse(
                lottery.getTicket(),
                lottery.getAmount(),
                lottery.getPrice());
    }


    public PurchaseLotteryResponse createPurchaseLotteryResponse(UserTicket userTicket){
        return new PurchaseLotteryResponse(
                userTicket.getNo(),
                userTicket.getTicket(),
                userTicket.getAmount(),
                userTicket.getPrice()
        );
    }

    public GetLotteryResponse createGetLotteryResponse(List<String> tickets) {
        GetLotteryResponse getLotteryResponse = new GetLotteryResponse(
                tickets
        );
        return getLotteryResponse;
    }


    public ViewLotteryPurchase createViewLotteryPurchase(List<UserTicketSummary> userTicketSummaryList) {

        List<String> tickets = new ArrayList<>();
        double totalBill = 0.0;
        long totalAmount = 0;

        for(UserTicketSummary userTicketSummary:userTicketSummaryList){
            tickets.add(userTicketSummary.getTicket());
            totalBill += userTicketSummary.getTotalTicketBillPrice();
            totalAmount += userTicketSummary.getTotalTicketAmount();
        }

        return new ViewLotteryPurchase(
                tickets,
                totalAmount,
                totalBill
        );
    }

    public SellBackLotteryResponse createGellBackLotteryResponse(String ticket) {
       return new SellBackLotteryResponse(ticket);
    }

}
