package com.kbtg.bootcamp.posttest.lottery.service;


import com.kbtg.bootcamp.posttest.exceptions.InternalServerException;
import com.kbtg.bootcamp.posttest.exceptions.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.model.creator.ModelCreator;
import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.model.entity.UserTicket;
import com.kbtg.bootcamp.posttest.lottery.model.response.CreateLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.model.response.GetLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.model.response.PurchaseLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.model.response.ViewLotteryPurchase;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.repository.UserTicketRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public CreateLotteryResponse createlLottery(LotteryDto lotteryDto){
        Lottery lottery = this.modelCreator.createLotteryEntity(lotteryDto);
        try{
            this.lotteryRepository.save(lottery);
        }catch(Exception e){
            throw new InternalServerException("Failed to create Lottery");
        }

        return this.modelCreator.createCreateLotteryResponse(lottery);
    }

    public GetLotteryResponse getAllLottery(){
        List<String> tickets = this.lotteryRepository.getAllTicket();
        return this.modelCreator.createGetLotteryResponse(tickets);
    }

    public ViewLotteryPurchase getLotteryByUserId(String userId){
        Optional<List<UserTicket>> optionalUserTicket = this.userTicketRepository.findByUserId(userId);

        if(optionalUserTicket.isEmpty()){
            throw new NotFoundException("Not found lottery purchase transaction");
        }

        List<UserTicket> userTickets = optionalUserTicket.get();

        Map<String, Double> result = userTickets.stream()
                .collect(Collectors.groupingBy(
                        UserTicket::getTicket,
                        Collectors.summingDouble(ticket -> ticket.getAmount() * ticket.getPrice())
                ));

        List<String> ticketList = userTickets.stream()
                .map(UserTicket::getTicket)
                .collect(Collectors.toList());


        return null;
    }


    public PurchaseLotteryResponse purchaseLottery(UserTicketDto userTicketDto){
        Optional<Lottery> optionalLottery = this.lotteryRepository.findById(userTicketDto.getTicket());

        if(optionalLottery.isEmpty()) {
            throw new NotFoundException("Ticket not found");
        }

        Lottery lottery = optionalLottery.get();

        UserTicket userTicket = this.modelCreator.createUserTicketEntity(userTicketDto);

        if(0==userTicket.getAmount()){
            userTicket.setAmount(1);
        }

        userTicket.setPrice(this.calulateLotteryPrice(lottery.getPrice(),userTicket.getAmount()));

        UserTicket userTicketDb = null;
        try{
            userTicketDb = this.userTicketRepository.save(userTicket);
        }catch(Exception e){
            throw new InternalServerException("Failed to purchase Lottery");
        }
        return this.modelCreator.createPurchaseLotteryResponse(userTicketDb);
    }

    private Double calulateLotteryPrice(Double price,int amount){
        return price*amount;
    }



}
