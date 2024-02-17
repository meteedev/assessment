package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.request.LotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.model.response.LotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }





    @Operation(summary = "admin create lottery")
    @RequestMapping(value = "/admin/lotteries", method = RequestMethod.POST)
    public LotteryResponse createLottery(
            @RequestBody @Valid LotteryRequest lotteryRequest
    ) {
        LotteryDto accountDto =  this.convertLotteryRequestToDto();
        LotteryResponse lotteryResponse =  null;
        //System.out.println(accountResponse.toString());
        return lotteryResponse;
    }


    @Operation(summary = "get lottery")
    @RequestMapping(value = "/lotteries", method = RequestMethod.GET)
    public LotteryResponse getLottery(
            @RequestBody @Valid LotteryRequest lotteryRequest
    ) {
        LotteryDto accountDto =  this.convertLotteryRequestToDto();
        LotteryResponse lotteryResponse =  null;
        //System.out.println(accountResponse.toString());
        return lotteryResponse;
    }


    @Operation(summary = "buy lottery")
    @RequestMapping(value = "/users/{userId}/lotteries/{ticketId}", method = RequestMethod.POST)
    public LotteryResponse purchaseLottery(
            @PathVariable(name = "userId") Integer userId,
            @PathVariable(name = "ticketId") Integer ticketId,
            @RequestBody @Valid LotteryRequest lotteryRequest
    ) {
        LotteryDto accountDto =  this.convertLotteryRequestToDto();
        LotteryResponse lotteryResponse =  null;
        //System.out.println(accountResponse.toString());
        return lotteryResponse;
    }

    @Operation(summary = "cancel lottery")
    @RequestMapping(value = "/users/{userId}/lotteries/{ticketId}", method = RequestMethod.DELETE)
    public LotteryResponse cancelLotteryPerchase(
            @PathVariable(name = "userId") Integer userId,
            @PathVariable(name = "ticketId") Integer ticketId,
            @RequestBody @Valid LotteryRequest lotteryRequest
    ) {
        LotteryDto accountDto =  this.convertLotteryRequestToDto();
        LotteryResponse lotteryResponse =  null;
        //System.out.println(accountResponse.toString());
        return lotteryResponse;
    }




    private LotteryDto convertLotteryRequestToDto(){
        return null;
    }

}