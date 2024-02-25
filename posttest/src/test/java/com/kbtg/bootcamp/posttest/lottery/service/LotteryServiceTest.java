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
import com.kbtg.bootcamp.posttest.lottery.model.response.*;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.repository.UserTicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class LotteryServiceTest {

    @Mock
    LotteryRepository lotteryRepository;

    @Mock
    UserTicketRepository userTicketRepository;

    @Mock
    ModelCreator modelCreator;

    @InjectMocks
    LotteryService lotteryService;

    @Test
    @DisplayName("Create lottery")
    public void when_createLottery_Return_CreateLotteryResponse(){
        // Create a sample LotteryDto
        LotteryDto lotteryDto = new LotteryDto("123456",10,80.0);
        // Create a sample Lottery
        Lottery lottery = new Lottery();
        // Create a sample CreateLotteryResponse
        CreateLotteryResponse createLotteryResponse = new CreateLotteryResponse(lotteryDto.getTicket());

        // Mock the behavior of mapStructMapper
        when(modelCreator.mapLotteryDTOToLotteryEntity(any(LotteryDto.class))).thenReturn(lottery);
        when(modelCreator.mapLotteryEntityToCreateLotteryResponse(any(Lottery.class))).thenReturn(createLotteryResponse);

        // Mock the behavior of lotteryRepository
        when(lotteryRepository.findById(anyString())).thenReturn(Optional.empty()); // No duplicate ticket
        //when(lotteryRepository.findById(anyString())).thenReturn(Optional.of(lottery)); // No duplicate ticket

        // Mock the behavior of save method in lotteryRepository
        when(lotteryRepository.save(any(Lottery.class))).thenReturn(lottery);

        // Call the method you want to test
        CreateLotteryResponse result = lotteryService.createLottery(lotteryDto);

        // Assert the result or verify behavior
        assertNotNull(result);
        assertEquals(result.ticket(),lotteryDto.getTicket());
        // You can add more assertions based on the expected behavior of your method
    }

    @Test
    @DisplayName("Create lottery ticket duplicated")
    public void when_createLottery_Duplicated_Throws_InternalServerException(){
        // Create a sample LotteryDto
        LotteryDto lotteryDto = new LotteryDto("123456",10,80.0);
        // Create a sample Lottery
        Lottery lottery = new Lottery();
        // Create a sample CreateLotteryResponse
        CreateLotteryResponse createLotteryResponse = new CreateLotteryResponse(lotteryDto.getTicket());

        when(modelCreator.mapLotteryDTOToLotteryEntity(any(LotteryDto.class))).thenReturn(lottery);
        when(modelCreator.mapLotteryEntityToCreateLotteryResponse(any(Lottery.class))).thenReturn(createLotteryResponse);
        when(lotteryRepository.findById(anyString())).thenReturn(Optional.of(lottery)); // duplicate ticket

        assertThrows(InternalServerException.class, () -> lotteryService.createLottery(lotteryDto));

    }

    @Test
    @DisplayName("Create lottery ticket have incident RuntimeException")
    public void when_createLottery_have_RuntimeException_Throws_InternalServerException(){
        LotteryDto lotteryDto = new LotteryDto("123456",10,80.0);
        // Create a sample Lottery
        Lottery lottery = new Lottery();

        // Mock the behavior of mapStructMapper
        when(modelCreator.mapLotteryDTOToLotteryEntity(any(LotteryDto.class))).thenReturn(lottery);

        // Mock the behavior of lotteryRepository
        when(lotteryRepository.findById(anyString())).thenReturn(Optional.empty());

        // Mock the behavior of save method in lotteryRepository to throw a RuntimeException
        doThrow(new RuntimeException("Simulated RuntimeException")).when(lotteryRepository).save(any(Lottery.class));

        // Use assertThrows to verify the expected exception
        InternalServerException thrownException = assertThrows(InternalServerException.class, () -> lotteryService.createLottery(lotteryDto));

        // Optionally, you can assert further details about the exception if needed
        assertEquals("Simulated RuntimeException", thrownException.getMessage());
    }


    @Test
    @DisplayName("Get All lotteries ")
    public void when_getAllLottery_Return_GetLotteryResponse() {
        // Create a sample list of tickets
        List<String> sampleTickets = Arrays.asList("111111", "222222", "333333");

        // Mock the behavior of lotteryRepository
        when(lotteryRepository.getAllTicket()).thenReturn(sampleTickets);

        // Mock the behavior of modelCreator
        when(modelCreator.createGetLotteryResponse(sampleTickets))
                .thenReturn(new GetLotteryResponse(sampleTickets));

        // Call the method you want to test
        GetLotteryResponse result = lotteryService.getAllLottery();

        // Assert the result or verify behavior
        assertNotNull(result);
    }


    @Test
    @DisplayName("Get All lotteries not found ticket ")
    public void when_getAllLottery_NotFound_Throws_NotFoundException() {
        // Mock the behavior of lotteryRepository to return an empty list
        when(lotteryRepository.getAllTicket()).thenReturn(Collections.emptyList());

        // Use assertThrows to verify the expected exception
        NotFoundException thrownException = assertThrows(NotFoundException.class, () -> lotteryService.getAllLottery());

        // Optionally, you can assert further details about the exception if needed
        assertEquals(LotteryModuleConstant.MSG_VIEW_TICKETS_NOT_FOUND, thrownException.getMessage());
        // Add more assertions based on your specific requirements
    }


    @Test
    @DisplayName("Purchase lottery")
    public void when_Purchase_Lottery_Return_PurchaseLotteryResponse() {
        // Create a sample UserTicketDto
        String ticket = "123456";
        Integer amount = 1;
        String userId = "1234567890";
        Double price = 80.0;

        UserTicketDto userTicketDto = new UserTicketDto();
        userTicketDto.setTicket(ticket);
        userTicketDto.setAmount(amount);
        userTicketDto.setUserId(userId);

        Lottery lottery =  new Lottery();
        lottery.setTicket(ticket);
        lottery.setPrice(price);
        lottery.setAmount(10);

        UserTicket userTicketDb = new UserTicket();
        userTicketDb.setNo(1);


        // Mock the behavior of lotteryRepository
        when(lotteryRepository.findById(userTicketDto.getTicket())).thenReturn(Optional.of(lottery));

        // Mock the behavior of mapStructMapper
        when(modelCreator.mapUserTicketDTOToUserTicketEntity(any(UserTicketDto.class)))
                .thenReturn(new UserTicket(/* your UserTicket properties here */));

        // Mock the behavior of userTicketRepository
        when(userTicketRepository.save(any(UserTicket.class))).thenReturn(userTicketDb);

        when(modelCreator.mapUserTicketToPurchaseLotteryResponse(any(UserTicket.class)))
                .thenReturn(new PurchaseLotteryResponse(String.valueOf(userTicketDb.getNo())));

        // Call the method you want to test
        PurchaseLotteryResponse result = lotteryService.purchaseLottery(userTicketDto);

        // Assert the result or verify behavior
        assertNotNull(result);
    }

    @Test
    @DisplayName("Purchase lottery that master table not found")
    public void when_Purchase_Lottery_Lottery_NotFound_in_table_Throws_UnProcessException() {
        // Create a sample UserTicketDto

        String ticket = "123456";
        Integer amount = 1;
        String userId = "1234567890";

        UserTicketDto userTicketDto = new UserTicketDto();
        userTicketDto.setTicket(ticket);
        userTicketDto.setAmount(amount);
        userTicketDto.setUserId(userId);

        // Mock the behavior of lotteryRepository to return an empty Optional
        when(lotteryRepository.findById(userTicketDto.getTicket())).thenReturn(Optional.empty());

        // Call the method you want to test and use assertThrows to verify the expected exception
        var thrownException = assertThrows(UnProcessException.class, () -> lotteryService.purchaseLottery(userTicketDto));

        // Optionally, you can assert further details about the exception if needed
        assertEquals(LotteryModuleConstant.MSG_PURCHASE_TICKET_NOT_FOUND_IN_MASTER_TABLE, thrownException.getMessage());
        // Add more assertions based on your specific requirements
    }


    @Test
    @DisplayName("View Purchase lottery")
    public void when_View_Purchase_Lottery_Return_ViewLotteryPurchase() {
        // Create a sample userId
        String userId = "1234567890";



        UserTicketSummary userTicketSummary1 = new UserTicketSummary("123456","1111111111",2L,160.0);
        UserTicketSummary userTicketSummary2 = new UserTicketSummary("111222", "2222222222",1L,100.0);

        // Create a sample list of UserTicketSummary
        List<UserTicketSummary> sampleUserTicketSummaries = Arrays.asList(
                userTicketSummary1,
                userTicketSummary2
        );

        // Mock the behavior of userTicketRepository
        when(userTicketRepository.findSumPriceAmountByUserId(userId)).thenReturn(sampleUserTicketSummaries);

        ViewLotteryPurchase viewLotteryPurchase = new ViewLotteryPurchase(
                Arrays.asList("123456","111222"),
                3,
                260.0
        );


        // Mock the behavior of modelCreator
        when(modelCreator.createViewLotteryPurchase(sampleUserTicketSummaries))
                .thenReturn(viewLotteryPurchase);

        // Call the method you want to test
        ViewLotteryPurchase result = lotteryService.getLotteryByUserId(userId);

        // Assert the result or verify behavior
        assertNotNull(result);
        // Add more assertions based on the expected behavior of your method
    }

    @Test
    @DisplayName("View Purchase lottery not found transaction")
    public void when_View_Purchase_Lottery_NotFound_transaction_Throws_NotFoundException() {
        // Create a sample userId
        String userId = "1234567890";

        // Mock the behavior of userTicketRepository to return an empty list
        when(userTicketRepository.findSumPriceAmountByUserId(userId)).thenReturn(Collections.emptyList());

        // Mock the behavior of modelCreator (not needed for this case)

        // Use assertThrows to verify the expected exception
        NotFoundException thrownException = assertThrows(NotFoundException.class, () -> lotteryService.getLotteryByUserId(userId));

        // Optionally, you can assert further details about the exception if needed
        assertEquals(LotteryModuleConstant.MSG_USER_TICKET_NOT_FOUND, thrownException.getMessage());
        // Add more assertions based on your specific requirements
    }


    @Test
    @DisplayName("Sell back lottery")
    public void when_Sell_back_Lottery_Return_SellBackLotteryResponse() {

        // Create a sample UserTicketDto
        String ticket = "123456";
        Integer amount = 1;
        String userId = "1234567890";
        Double price = 80.0;

        UserTicketDto userTicketDto = new UserTicketDto();
        userTicketDto.setTicket(ticket);
        userTicketDto.setAmount(amount);
        userTicketDto.setUserId(userId);

        Lottery lottery;
        lottery = new Lottery();
        lottery.setTicket(ticket);
        lottery.setPrice(price);
        lottery.setAmount(10);

        UserTicket userTicketDb = new UserTicket();
        userTicketDb.setNo(1);

        UserTicketSummary userTicketSummary = new UserTicketSummary("123456","1111111111",2L,160.0);

        SellBackLotteryResponse sellBackLotteryResponse = new SellBackLotteryResponse(userTicketSummary.getTicket());

        // Create a sample Lottery
        Lottery sampleLottery = new Lottery();
        sampleLottery.setTicket(userTicketSummary.getTicket());
        sampleLottery.setAmount(10);


        // Mock the behavior of userTicketRepository
        when(userTicketRepository.findSumPriceAmountByUserIdTicketId(userTicketDto.getUserId(), userTicketDto.getTicket()))
                .thenReturn(Optional.of(userTicketSummary));

        // Mock the behavior of lotteryRepository
        when(lotteryRepository.findById(userTicketDto.getTicket())).thenReturn(Optional.of(sampleLottery));

        // Mock the behavior of modelCreator
        when(modelCreator.createSellBackLotteryResponse(userTicketSummary.getTicket()))
                .thenReturn(sellBackLotteryResponse);

        // Call the method you want to test
        SellBackLotteryResponse result = lotteryService.sellBackLottery(userTicketDto);

        // Assert the result or verify behavior
        assertNotNull(result);
        // Add more assertions based on the expected behavior of your method
    }

    @Test
    @DisplayName("Sell back lottery not found transaction")
    public void when_Sell_back_Lottery_NotFound_Transaction_Throws_NotFoundException() {
        // Create a sample UserTicketDto
        UserTicketDto userTicketDto = new UserTicketDto();

        // Mock the behavior of userTicketRepository to return an empty Optional
        when(userTicketRepository.findSumPriceAmountByUserIdTicketId(userTicketDto.getUserId(), userTicketDto.getTicket()))
                .thenReturn(Optional.empty());


        // Use assertThrows to verify the expected exception
        NotFoundException thrownException = assertThrows(NotFoundException.class, () -> lotteryService.sellBackLottery(userTicketDto));

        // Optionally, you can assert further details about the exception if needed
        assertEquals(LotteryModuleConstant.MSG_USER_TICKET_NOT_FOUND, thrownException.getMessage());
        // Add more assertions based on your specific requirements
    }

    @Test
    @DisplayName("Sell back lottery not found transaction")
    public void when_Sell_back_Lottery_NotFound_Lottery_in_table_Throws_NotFoundException() {

        UserTicketSummary userTicketSummary = new UserTicketSummary("123456","1111111111",2L,160.0);

        // Create a sample UserTicketDto
        UserTicketDto userTicketDto = new UserTicketDto(/* your userTicketDto properties here */);

        // Mock the behavior of userTicketRepository to return a sample UserTicketSummary
        when(userTicketRepository.findSumPriceAmountByUserIdTicketId(userTicketDto.getUserId(), userTicketDto.getTicket()))
                .thenReturn(Optional.of(userTicketSummary));

        // Mock the behavior of lotteryRepository to return an empty Optional
        when(lotteryRepository.findById(userTicketDto.getTicket())).thenReturn(Optional.empty());

        // Mock the behavior of modelCreator (not needed for this case)

        // Use assertThrows to verify the expected exception
        UnProcessException thrownException = assertThrows(UnProcessException.class, () -> lotteryService.sellBackLottery(userTicketDto));

        // Optionally, you can assert further details about the exception if needed
        assertEquals(LotteryModuleConstant.MSG_TICKET_NOT_FOUND, thrownException.getMessage());
        // Add more assertions based on your specific requirements
    }


}