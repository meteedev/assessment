package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.exceptions.InternalServerException;
import com.kbtg.bootcamp.posttest.exceptions.NotFoundException;
import com.kbtg.bootcamp.posttest.exceptions.UnProcessException;
import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;
import com.kbtg.bootcamp.posttest.lottery.model.creator.ModelCreator;
import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketSummary;
import com.kbtg.bootcamp.posttest.lottery.model.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.model.entity.UserTicket;
import com.kbtg.bootcamp.posttest.lottery.model.mapper.MapStructMapper;
import com.kbtg.bootcamp.posttest.lottery.model.response.*;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.repository.UserTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LotteryService {

    private final LotteryRepository lotteryRepository;
    private final UserTicketRepository userTicketRepository;
    private final ModelCreator modelCreator;

    private final MapStructMapper mapStructMapper;


    public LotteryService(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository, ModelCreator modelCreator, MapStructMapper mapStructMapper) {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
        this.modelCreator = modelCreator;
        this.mapStructMapper = mapStructMapper;
    }

    @Transactional
    public CreateLotteryResponse createLottery(LotteryDto lotteryDto){
        Lottery lottery = this.mapStructMapper.mapLotteryDTOToEntity(lotteryDto);
        Optional<Lottery>  optionalLotteryDuplicate = this.lotteryRepository.findById(lotteryDto.getTicket());
        if(optionalLotteryDuplicate.isPresent()){
            throw new InternalServerException(LotteryModuleConstant.MSG_CREATE_TICKET_DUPLICATE);
        }
        try {
            this.lotteryRepository.save(lottery);
        }catch (RuntimeException e){
            throw new InternalServerException(e.getMessage());
        }catch(Exception e){
            throw new InternalServerException(LotteryModuleConstant.MSG_CREATE_TICKET_FAIL);
        }
        return this.mapStructMapper.mapLotteryEntityToCreateLotteryResponse(lottery);
    }


    public GetLotteryResponse getAllLottery(){
        List<String> tickets = this.lotteryRepository.getAllTicket();

        if(tickets.isEmpty()){
            throw new NotFoundException(LotteryModuleConstant.MSG_VIEW_TICKETS_NOT_FOUND);
        }

        return this.modelCreator.createGetLotteryResponse(tickets);
    }

    public ViewLotteryPurchase getLotteryByUserId(String userId){
        List<UserTicketSummary> userTicketSummaries = this.userTicketRepository.findSumPriceAmountByUserId(userId);

        if(userTicketSummaries.isEmpty()){
            throw new NotFoundException(LotteryModuleConstant.MSG_USER_TICKET_NOT_FOUND);
        }

        return this.modelCreator.createViewLotteryPurchase(userTicketSummaries);
    }


    @Transactional
    public PurchaseLotteryResponse purchaseLottery(UserTicketDto userTicketDto){
        Optional<Lottery> optionalLottery = this.lotteryRepository.findById(userTicketDto.getTicket());
        if(optionalLottery.isEmpty()) {
            throw new UnProcessException(LotteryModuleConstant.MSG_PURCHASE_TICKET_NOTFOUND_IN_MASTER_TABLE);
        }

        Lottery lottery = optionalLottery.get();
        Integer purchaseAmount = this.getDefaultPurchaseAmount(userTicketDto.getAmount());

        if(lottery.getAmount()<purchaseAmount){
            throw new UnProcessException(LotteryModuleConstant.MSG_PURCHASE_TICKET_AMOUNT_NOT_ENOUGH);
        }

        Integer newLotteryMasterAmount = lottery.getAmount()-purchaseAmount;
        lottery.setAmount(newLotteryMasterAmount);

        UserTicket userTicket = this.mapStructMapper.mapUserTicketDTOToUserTicket(userTicketDto);
        userTicket.setAmount(purchaseAmount);
        userTicket.setPrice(lottery.getPrice());
        userTicket.setTotalBill(this.calculateLotteryPrice(lottery.getPrice(),userTicket.getAmount()));

        UserTicket userTicketDb;
        try{
            userTicketDb = this.userTicketRepository.save(userTicket);
            this.lotteryRepository.save(lottery);
        }catch(Exception e){
            throw new InternalServerException(LotteryModuleConstant.MSG_PURCHASE_FAIL);
        }

        return this.mapStructMapper.mapUserTicketToPurchaseLotteryResponse(userTicketDb);
    }


    @Transactional
    public SellBackLotteryResponse sellBackLottery(UserTicketDto userTicketDto){

        Optional<UserTicketSummary> optionalUserTicketSummary = this.userTicketRepository.findSumPriceAmountByUserIdTicketId(userTicketDto.getUserId(),userTicketDto.getTicket());
        if(optionalUserTicketSummary.isEmpty()){
            throw new NotFoundException(LotteryModuleConstant.MSG_USER_TICKET_NOT_FOUND);
        }

        Optional<Lottery> optionalLottery = this.lotteryRepository.findById(userTicketDto.getTicket());
        if(optionalLottery.isEmpty()){
            throw new UnProcessException(LotteryModuleConstant.MSG_TICKET_NOT_FOUND);
        }
        UserTicketSummary  userTicketSummary = optionalUserTicketSummary.get();

        Lottery lottery = optionalLottery.get();
        Integer newLotteryAmount = lottery.getAmount()+userTicketSummary.getTotalTicketAmount().intValue();
        lottery.setAmount(newLotteryAmount);

        try{
            this.userTicketRepository.deleteUserTicketByUserIdTicket(userTicketDto.getUserId(),userTicketDto.getTicket());
            this.lotteryRepository.save(lottery);
        }catch (Exception e){
            throw new InternalServerException(LotteryModuleConstant.MSG_SELL_BACK_FAIL);
        }

        return this.modelCreator.createSellBackLotteryResponse(userTicketSummary.getTicket());
    }

    private Double calculateLotteryPrice(Double price,int amount){
        return price*amount;
    }

    private Integer getDefaultPurchaseAmount(Integer amount){
        return Objects.requireNonNullElse(amount, LotteryModuleConstant.DEFAULT_PURCHASE_TICKET_AMOUNT);
    }


}
