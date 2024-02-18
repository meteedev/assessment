package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.lottery.model.creator.ModelCreator;
import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.request.CreateRequest;
import com.kbtg.bootcamp.posttest.lottery.model.request.PurchaseRequest;
import com.kbtg.bootcamp.posttest.lottery.model.response.CreateLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.model.response.GetLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.model.response.PurchaseLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.model.response.ViewLotteryPurchase;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class LotteryController {

    private final LotteryService lotteryService;
    private final ModelCreator modelCreator;

    public LotteryController(LotteryService lotteryService, ModelCreator modelCreator) {
        this.lotteryService = lotteryService;
        this.modelCreator = modelCreator;
    }


    @Operation(summary = "admin create lottery")
    @RequestMapping(value = "/admin/lotteries", method = RequestMethod.POST)
    public CreateLotteryResponse createLottery(
            @RequestBody @Valid CreateRequest createRequest
    ) {
        LotteryDto lotteryDto =  this.modelCreator.createLotteryDto(createRequest);
        return lotteryService.createlLottery(lotteryDto);
    }


    @Operation(summary = "get lottery")
    @RequestMapping(value = "/lotteries", method = RequestMethod.GET)
    public GetLotteryResponse getAllLottery() {
        return this.lotteryService.getAllLottery();
    }


    @Operation(summary = "buy lottery")
    @RequestMapping(value = "/users/{userId}/lotteries/{ticketId}", method = RequestMethod.POST)
    public PurchaseLotteryResponse purchaseLottery(
            @PathVariable(name = "userId") String userId,
            @PathVariable(name = "ticketId") String ticketId,
            @RequestBody @Valid PurchaseRequest purchaseRequest
    ) {
        UserTicketDto userTicketDto = this.modelCreator.createUserTicketDto(userId,ticketId, purchaseRequest);
        return this.lotteryService.purchaseLottery(userTicketDto);
    }

    @Operation(summary = "view lottery was purchased by user")
    @RequestMapping(value = "/users/{userId}/lotteries", method = RequestMethod.GET)
    public ViewLotteryPurchase viewLotteryPurchaseByUser(
            @PathVariable(name = "userId") String userId
    ) {
        this.lotteryService.getLotteryByUserId(userId);
        return null;
    }



//    @Operation(summary = "cancel lottery")
//    @RequestMapping(value = "/users/{userId}/lotteries/{ticketId}", method = RequestMethod.DELETE)
//    public LotteryResponse cancelLotteryPerchase(
//            @PathVariable(name = "userId") Integer userId,
//            @PathVariable(name = "ticketId") Integer ticketId,
//            @RequestBody @Valid CreateRequest createRequest
//    ) {
//        LotteryDto accountDto = modelConverter.convertLotteryRequestToDto(createRequest);
//        LotteryResponse lotteryResponse =  null;
//        //System.out.println(accountResponse.toString());
//        return lotteryResponse;
//    }



}