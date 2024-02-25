package com.kbtg.bootcamp.posttest.lottery.model.creator;

import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketSummary;
import com.kbtg.bootcamp.posttest.lottery.model.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.model.entity.UserTicket;
import com.kbtg.bootcamp.posttest.lottery.model.request.CreateRequest;
import com.kbtg.bootcamp.posttest.lottery.model.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ModelCreatorTest {

	private ModelCreator modelCreator;
	@BeforeEach
	void setUp(){
		modelCreator = new ModelCreator();
	}

	@Test
	void createUserTicketDto() {
	String userId = "11111111111";
	String ticketId = "123456";
	UserTicketDto userTicketDto = modelCreator.createUserTicketDto(userId,ticketId);

	assertEquals(userId,userTicketDto.getUserId());
	assertEquals(ticketId,userTicketDto.getTicket());

	}

	@Test
	void createGetLotteryResponse() {
		List<String> tickets = Arrays.asList("11111","22222");
		GetLotteryResponse getLotteryResponse = modelCreator.createGetLotteryResponse(tickets);
		assertEquals(tickets.size(),getLotteryResponse.tickets().size());
	}

	@Test
	void createViewLotteryPurchase() {

		UserTicketSummary  userTicketSummary1 = new UserTicketSummary("T1","U1",2L,160.0);
		UserTicketSummary  userTicketSummary2 = new UserTicketSummary("T2","U2",1L,80.0);
		List<UserTicketSummary> userTicketSummaries = Arrays.asList(userTicketSummary1,userTicketSummary2);

		ViewLotteryPurchase viewLotteryPurchase = modelCreator.createViewLotteryPurchase(userTicketSummaries);
		assertEquals(3,viewLotteryPurchase.count());
		assertEquals(240.0,viewLotteryPurchase.cost());

	}

	@Test
	void createSellBackLotteryResponse() {
		String ticket = "T1";
		SellBackLotteryResponse sellBackLotteryResponse = modelCreator.createSellBackLotteryResponse(ticket);
		assertEquals(ticket,sellBackLotteryResponse.ticket());
	}

	@Test
	void mapCreateRequestToLotteryDTO(){
		String ticket = "T1";
		Double price = 80.0;
		Integer amount = 10;
		CreateRequest createRequest = new CreateRequest(ticket,price,amount);
		LotteryDto lotteryDto = modelCreator.mapCreateRequestToLotteryDTO(createRequest);
		assertEquals(ticket,lotteryDto.getTicket());
		assertEquals(price,lotteryDto.getPrice());
		assertEquals(amount,lotteryDto.getAmount());
	}


	@Test
	void mapLotteryDTOToLotteryEntity(){
		String ticket = "T1";
		Double price = 80.0;
		Integer amount = 10;

		LotteryDto lotteryDto = new LotteryDto(ticket,amount,price);
		Lottery lottery = modelCreator.mapLotteryDTOToLotteryEntity(lotteryDto);

		assertEquals(ticket,lottery.getTicket());
		assertEquals(price,lottery.getPrice());
		assertEquals(amount,lottery.getAmount());
	}

	@Test
	void  mapLotteryEntityToCreateLotteryResponse() {

		String ticket = "T1";
		Lottery lottery = new Lottery();

		lottery.setTicket(ticket);
		CreateLotteryResponse createLotteryResponse = modelCreator.mapLotteryEntityToCreateLotteryResponse(lottery);

		assertEquals(ticket,createLotteryResponse.ticket());

	}

	@Test
	void  mapUserTicketDTOToUserTicketEntity() {
		String ticket = "T1";
		String userId = "U1";
		UserTicketDto userTicketDto = new UserTicketDto();
		userTicketDto.setUserId(userId);
		userTicketDto.setTicket(ticket);

		UserTicket userTicket =  modelCreator.mapUserTicketDTOToUserTicketEntity(userTicketDto);
		assertEquals(ticket,userTicket.getTicket());
		assertEquals(userId,userTicket.getUserId());
	}


	@Test
	void mapUserTicketToPurchaseLotteryResponse(){
		int no  = 1;
		UserTicket userTicket = new UserTicket();
		userTicket.setNo(no);

		PurchaseLotteryResponse purchaseLotteryResponse = modelCreator.mapUserTicketToPurchaseLotteryResponse(userTicket);

		assertEquals(String.valueOf(no),purchaseLotteryResponse.id());
	}

}
