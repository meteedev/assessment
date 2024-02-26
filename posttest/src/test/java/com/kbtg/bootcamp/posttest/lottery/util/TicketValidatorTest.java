package com.kbtg.bootcamp.posttest.lottery.util;

import com.kbtg.bootcamp.posttest.exceptions.AppValidateException;
import org.junit.jupiter.api.Test;

import static com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant.*;
import static org.junit.jupiter.api.Assertions.*;

class TicketValidatorTest {
	@Test
	void when_input_ticket_null_Throws_AppValidateException(){
		try{
			TicketValidator.validateTicketIdFormat(null);
		}catch (AppValidateException e){
			assertEquals(MSG_INVALID_TICKET_FORMAT,e.getMessage());
		}


	}

	@Test
	void when_input_ticket_not_digit_Throws_AppValidateException(){
		String ticket = "T23456";
		try{
			TicketValidator.validateTicketIdFormat(ticket);
		}catch (AppValidateException e){
			assertEquals(MSG_INVALID_TICKET_FORMAT,e.getMessage());
		}
	}


}