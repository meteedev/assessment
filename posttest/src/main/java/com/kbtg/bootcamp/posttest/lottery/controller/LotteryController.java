package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.lottery.model.creator.ModelCreator;
import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.request.CreateRequest;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import com.kbtg.bootcamp.posttest.lottery.util.TicketValidator;
import com.kbtg.bootcamp.posttest.lottery.util.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createLottery(
            @RequestBody @Valid CreateRequest createRequest
    ) {
        LotteryDto lotteryDto =  this.modelCreator.createLotteryDto(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.lotteryService.createLottery(lotteryDto));
        //return this.lotteryService.createLottery(lotteryDto);
    }


    @Operation(summary = "get lottery")
    @RequestMapping(value = "/lotteries", method = RequestMethod.GET)


    public ResponseEntity getAllLottery() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.lotteryService.getAllLottery());
    }


    @Operation(summary = "buy lottery")
    @RequestMapping(value = "/users/{userId}/lotteries/{ticketId}", method = RequestMethod.POST)
    public ResponseEntity purchaseLottery(
            @PathVariable(name = "userId") String userId,
            @PathVariable(name = "ticketId") String ticketId
    ) {
        UserValidator.validateUserIdFormat(userId);
        TicketValidator.validateTicketIdFormat(ticketId);
        UserTicketDto userTicketDto = this.modelCreator.createUserTicketDto(userId,ticketId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.lotteryService.purchaseLottery(userTicketDto));

    }

    @Operation(summary = "view lottery was purchased by user")
    @RequestMapping(value = "/users/{userId}/lotteries", method = RequestMethod.GET)
    public ResponseEntity viewLotteryPurchaseByUser(
            @PathVariable(name = "userId") String userId
    ) {
        UserValidator.validateUserIdFormat(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.lotteryService.getLotteryByUserId(userId));
    }

    @Operation(summary = "refund lottery")
    @RequestMapping(value = "/users/{userId}/lotteries/{ticketId}", method = RequestMethod.DELETE)
    public ResponseEntity sellBackLottery(
            @PathVariable(name = "userId") String userId,
            @PathVariable(name = "ticketId") String ticketId
    ) {
        UserValidator.validateUserIdFormat(userId);
        TicketValidator.validateTicketIdFormat(ticketId);
        UserTicketDto userTicketDto = this.modelCreator.createUserTicketDto(userId,ticketId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(this.lotteryService.sellBackLottery(userTicketDto));

    }





}