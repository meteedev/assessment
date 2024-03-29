package com.kbtg.bootcamp.posttest.lottery.controller;
import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;
import com.kbtg.bootcamp.posttest.lottery.model.creator.ModelCreator;
import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.request.CreateRequest;
import com.kbtg.bootcamp.posttest.lottery.model.response.*;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import com.kbtg.bootcamp.posttest.lottery.util.TicketValidator;
import com.kbtg.bootcamp.posttest.lottery.util.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = LotteryModuleConstant.PATH_CREATE_LOTTERY)
	public ResponseEntity<CreateLotteryResponse> createLottery(
			@RequestBody @Valid CreateRequest createRequest
	) {

		LotteryDto lotteryDto = this.modelCreator.mapCreateRequestToLotteryDTO(createRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(this.lotteryService.createLottery(lotteryDto));
	}


	@Operation(summary = "get lottery")
	@GetMapping(value = LotteryModuleConstant.PATH_VIEW_ALL_LOTTERY)
	public ResponseEntity<GetLotteryResponse> getAllLottery() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(this.lotteryService.getAllLottery());
	}


	@Operation(summary = "buy lottery")
	@PostMapping(value = LotteryModuleConstant.PATH_PURCHASE_LOTTERY)
	public ResponseEntity<PurchaseLotteryResponse> purchaseLottery(
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
	@GetMapping(value = LotteryModuleConstant.PATH_VIEW_LOTTERY_BY_USER)
	public ResponseEntity<ViewLotteryPurchase> viewLotteryPurchaseByUser(
			@PathVariable(name = "userId") String userId
	) {
		UserValidator.validateUserIdFormat(userId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(this.lotteryService.getLotteryByUserId(userId));
	}

	@Operation(summary = "refund lottery")
	@DeleteMapping(value = LotteryModuleConstant.PATH_SELL_BACK_LOTTERY)
	public ResponseEntity<SellBackLotteryResponse> sellBackLottery(
			@PathVariable(name = "userId") String userId,
			@PathVariable(name = "ticketId") String ticketId
	) {
		UserValidator.validateUserIdFormat(userId);
		TicketValidator.validateTicketIdFormat(ticketId);
		UserTicketDto userTicketDto = this.modelCreator.createUserTicketDto(userId,ticketId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(this.lotteryService.sellBackLottery(userTicketDto));

	}





}