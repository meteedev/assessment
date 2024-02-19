package com.kbtg.bootcamp.posttest.lottery.service;


import com.kbtg.bootcamp.posttest.exceptions.InternalServerException;
import com.kbtg.bootcamp.posttest.exceptions.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.model.creator.ModelCreator;
import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketSummary;
import com.kbtg.bootcamp.posttest.lottery.model.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.model.entity.UserTicket;
import com.kbtg.bootcamp.posttest.lottery.model.response.*;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.repository.UserTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LotteryService {

    private final LotteryRepository lotteryRepository;
    private final UserTicketRepository userTicketRepository;
    private final ModelCreator modelCreator;


    public LotteryService(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository, ModelCreator modelCreator) {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
        this.modelCreator = modelCreator;
    }

    @Transactional
    public CreateLotteryResponse createlLottery(LotteryDto lotteryDto){
        Lottery lottery = this.modelCreator.createLotteryEntity(lotteryDto);

        Optional<Lottery>  optionalLotteryDuplicate = this.lotteryRepository.findById(lotteryDto.getTicket());

        if(optionalLotteryDuplicate.isPresent()){
            throw new InternalServerException("Failed to create Lottery duplicated ticket");
        }

        try {
            this.lotteryRepository.save(lottery);
        }catch (RuntimeException e){
            throw new InternalServerException(e.getMessage());
        }catch(Exception e){
            throw new InternalServerException("Failed to create Lottery");
        }

        return this.modelCreator.createCreateLotteryResponse(lottery);
    }


    public GetLotteryResponse getAllLottery(){
        List<String> tickets = this.lotteryRepository.getAllTicket();

        if(tickets.isEmpty()){
            throw new NotFoundException("Not found lottery");
        }

        return this.modelCreator.createGetLotteryResponse(tickets);
    }

    public ViewLotteryPurchase getLotteryByUserId(String userId){
        List<UserTicketSummary> userTicketSummaries = this.userTicketRepository.findSumPriceAmountByUserId(userId);

        if(userTicketSummaries.isEmpty()){
            throw new NotFoundException("Not found lottery purchase transaction");
        }

        return this.modelCreator.createViewLotteryPurchase(userTicketSummaries);
    }


    public PurchaseLotteryResponse purchaseLottery(UserTicketDto userTicketDto){
        Optional<Lottery> optionalLottery = this.lotteryRepository.findById(userTicketDto.getTicket());

        if(!optionalLottery.isPresent()) {
            throw new NotFoundException("Ticket not found");
        }

        Lottery lottery = optionalLottery.get();
        UserTicket userTicket = this.modelCreator.createUserTicketEntity(userTicketDto);

        if(0==userTicket.getAmount()){
            userTicket.setAmount(1);
        }

        userTicket.setPrice(lottery.getPrice());
        userTicket.setTotalBill(this.calulateLotteryPrice(lottery.getPrice(),userTicket.getAmount()));

        UserTicket userTicketDb = null;
        try{
            userTicketDb = this.userTicketRepository.save(userTicket);
        }catch(Exception e){
            throw new InternalServerException("Failed to purchase Lottery");
        }
        return this.modelCreator.createPurchaseLotteryResponse(userTicketDb);
    }



    @Transactional
    public SellBackLotteryResponse sellBackLottery(UserTicketDto userTicketDto){
        Optional<UserTicketSummary> optionalUserTicketSummary = this.userTicketRepository.findSumPriceAmountByUserIdTicketId(userTicketDto.getUserId(),userTicketDto.getTicket());

        System.out.println(optionalUserTicketSummary.isPresent());

        if(!optionalUserTicketSummary.isPresent()){
            throw new NotFoundException("Not found lottery purchase transaction");
        }

        UserTicketSummary  userTicketSummary = optionalUserTicketSummary.get();

        try{
            this.userTicketRepository.deleteUserTicketByUserIdTicket(userTicketDto.getUserId(),userTicketDto.getTicket());
        }catch (Exception e){
            e.printStackTrace();
            throw new InternalServerException("Failed to sell back Lottery");
        }

        return this.modelCreator.createGellBackLotteryResponse(userTicketSummary.getTicket());
    }

    private Double calulateLotteryPrice(Double price,int amount){
        return price*amount;
    }



}
