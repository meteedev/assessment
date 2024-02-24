package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.config.SpringSecurityConfig;
import com.kbtg.bootcamp.posttest.exceptions.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;
import com.kbtg.bootcamp.posttest.lottery.model.creator.ModelCreator;
import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.mapper.MapStructMapper;
import com.kbtg.bootcamp.posttest.lottery.model.request.CreateRequest;
import com.kbtg.bootcamp.posttest.lottery.model.response.*;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import org.apache.catalina.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.test.context.support.WithMockUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Import(SpringSecurityConfig.class)
@WebMvcTest(LotteryController.class)
public class LotteryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelCreator modelCreator; // Mocking the ModelCreator bean
    @MockBean
    private LotteryService lotteryService;
    @MockBean
    private MapStructMapper mapStructMapper;

    @Autowired
    private ObjectMapper objectMapper;




    @Test
    @DisplayName("Admin role create lottery")
    @WithMockUser(username = "admin",password = "password",roles = "ADMIN")
    public void given_AdminRoleUser_When_CreateLottery_Then_Return_status_OK_and_Ticket() throws Exception {

        String ticketId = "123456";
        Double price = 80.0;
        Integer amount = 10;

        CreateRequest createRequest = new CreateRequest(ticketId,price,amount);
        LotteryDto lotteryDto = this.mapStructMapper.mapCreateRequestToDTO(createRequest);


        when(lotteryService.createLottery(lotteryDto))
                .thenReturn(new CreateLotteryResponse(ticketId));

        this.mockMvc.perform(post(LotteryController.PATH_CREATE_LOTTERY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(createRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ticket").value(ticketId));

    }

    @Test
    @DisplayName("User role create lottery")
    @WithMockUser(username = "admin",password = "password",roles = "USER")
    public void given_UserRoleUser_When_CreateLottery_Then_Return_status_UNAUTHORIZED() throws Exception {

        String ticketId = "123456";
        Double price = 80.0;
        Integer amount = 10;

        CreateRequest createRequest = new CreateRequest(ticketId,price,amount);
        LotteryDto lotteryDto = this.mapStructMapper.mapCreateRequestToDTO(createRequest);
        CreateLotteryResponse createLotteryResponse = new CreateLotteryResponse(ticketId);;

        when(lotteryService.createLottery(lotteryDto))
                .thenReturn(new CreateLotteryResponse(ticketId));

        this.mockMvc.perform(post(LotteryController.PATH_CREATE_LOTTERY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(createRequest)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("View all lotteries")
    public void when_GetAllLottery_Then_Return_status_OK_and_TicketsList() throws Exception {
        List<String> ticketList = new ArrayList<>();
        ticketList.add("123456");
        ticketList.add("111222");
        GetLotteryResponse getLotteryResponse = new GetLotteryResponse(ticketList);

        when(lotteryService.getAllLottery()).thenReturn(getLotteryResponse);

        this.mockMvc.perform(get(LotteryController.PATH_VIEW_ALL_LOTTERY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"tickets\": [\"123456\", \"111222\"]}"));
    }


    @Test
    @DisplayName("View lotteries and not found lotteries")
    public void when_GetAllLottery_Notfound_Then_Return_status_NOTFOUND_and_errorMsg() throws Exception {

        when(lotteryService.getAllLottery()).thenThrow(new NotFoundException(LotteryModuleConstant.MSG_VIEW_TICKETS_NOT_FOUND));
        this.mockMvc.perform(get(LotteryController.PATH_VIEW_ALL_LOTTERY))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(LotteryModuleConstant.MSG_VIEW_TICKETS_NOT_FOUND));
    }

    @Test
    @DisplayName("User view purchased lottery transactions lotteries")
    public void when_UserViewPurchasedLottery_Found_Then_Return_status_OK_and_ViewLotteryPurchase() throws Exception {

        String userId = "1234567890";
        List<String> ticketList = new ArrayList<>();
        ticketList.add("123456");
        ticketList.add("111222");
        ViewLotteryPurchase viewLotteryPurchase = new ViewLotteryPurchase(ticketList,ticketList.size(),200.0);

        when(lotteryService.getLotteryByUserId(userId)).thenReturn(viewLotteryPurchase);

        this.mockMvc.perform(get(LotteryController.PATH_VIEW_LOTTERY_BY_USER,userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.cost").value(200))
                .andExpect(jsonPath("$.tickets").value(ticketList));
    }


    @Test
    @DisplayName("User view purchased lottery transactions lotteries not found")
    public void when_UserViewPurchasedLottery_NotFound_Then_Return_status_NOTFOUND_and_errorMsg()throws Exception {
        String userId = "1234567890";

        when(lotteryService.getLotteryByUserId(userId)).thenThrow(new NotFoundException(LotteryModuleConstant.MSG_USER_TICKET_NOT_FOUND));

        this.mockMvc.perform(get(LotteryController.PATH_VIEW_LOTTERY_BY_USER,userId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(LotteryModuleConstant.MSG_USER_TICKET_NOT_FOUND));
    }

    @Test
    @DisplayName("View purchased lottery transactions and input invalid user_id format")
    public void when_InputInvalidUserIdFormat_ViewPurchasedLottery_Then_Return_status_BADREQUEST_and_errorMsg() throws Exception {

        String userId = "1234567";

        this.mockMvc.perform(get(LotteryController.PATH_VIEW_LOTTERY_BY_USER,userId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(LotteryModuleConstant.MSG_INVALID_USER_FORMAT));

    }


    @Test
    @DisplayName("Purchase lottery input invalid user_id format")
    public void when_InputInvalidUserIdFormat_PurchasedLottery_Then_Return_status_BADREQUEST_and_errorMsg() throws Exception {

        String userId = "1234567";
        String ticketId = "123";

        this.mockMvc.perform(post(LotteryController.PATH_PURCHASE_LOTTERY,userId,ticketId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(LotteryModuleConstant.MSG_INVALID_USER_FORMAT));

    }


    @Test
    @DisplayName("Purchase lottery input invalid ticket format")
    public void when_PurchasedLottery_Then_Return_status_BADREQUEST_and_errorMsg() throws Exception {

        String userId = "1234567890";
        String ticketId = "123";

        this.mockMvc.perform(post(LotteryController.PATH_PURCHASE_LOTTERY,userId,ticketId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(LotteryModuleConstant.MSG_INVALID_TICKET_FORMAT));

    }


    @Test
    @DisplayName("Purchase lottery")
    public void when_InputInvalidTicketFormat_PurchasedLottery_Then_Return_status_CREATED_and_TransactionsId() throws Exception {
        String userId = "1234567890";
        String ticketId = "123456";

        UserTicketDto userTicketDto= this.modelCreator.createUserTicketDto(userId,ticketId);
        PurchaseLotteryResponse purchaseLotteryResponse = new PurchaseLotteryResponse("1");

        when(lotteryService.purchaseLottery(userTicketDto)).thenReturn(purchaseLotteryResponse);
        this.mockMvc.perform(post(LotteryController.PATH_PURCHASE_LOTTERY,userId,ticketId))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }


    @Test
    @DisplayName("Sell back lottery input invalid user_id format")
    public void when_InputInvalidUserIdFormat_SellBackLottery_Then_Return_status_BADREQUEST_and_errorMsg() throws Exception {

        String userId = "1234567";
        String ticketId = "123";

        this.mockMvc.perform(delete(LotteryController.PATH_SELL_BACK_LOTTERY,userId,ticketId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(LotteryModuleConstant.MSG_INVALID_USER_FORMAT));

    }


    @Test
    @DisplayName("Sell back lottery input invalid ticket format")
    public void when_InputInvalidTicketFormat_SellBackLottery_Then_Return_status_BADREQUEST_and_errorMsg() throws Exception {

        String userId = "1234567890";
        String ticketId = "123";

        this.mockMvc.perform(delete(LotteryController.PATH_SELL_BACK_LOTTERY,userId,ticketId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(LotteryModuleConstant.MSG_INVALID_TICKET_FORMAT));

    }


    @Test
    @DisplayName("Sell back lottery ")
    public void when_SellBackLottery_Then_Return_status_BADREQUEST_and_errorMsg() throws Exception {

        String userId = "1234567890";
        String ticketId = "123456";

        UserTicketDto userTicketDto= this.modelCreator.createUserTicketDto(userId,ticketId);
        SellBackLotteryResponse sellBackLotteryResponse = new SellBackLotteryResponse(ticketId);

        when(lotteryService.sellBackLottery(userTicketDto)).thenReturn(sellBackLotteryResponse);
        this.mockMvc.perform(delete(LotteryController.PATH_SELL_BACK_LOTTERY,userId,ticketId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ticket").value(ticketId));

    }



}