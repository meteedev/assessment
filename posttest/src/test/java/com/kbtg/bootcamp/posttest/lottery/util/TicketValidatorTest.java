package com.kbtg.bootcamp.posttest.lottery.util;

import com.kbtg.bootcamp.posttest.exceptions.AppValidateException;
import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

	@Test
	void testPrivateConstructor() {
		// Use reflection to access the private constructor
		try {
			Constructor<TicketValidator> constructor = TicketValidator.class.getDeclaredConstructor();
			constructor.setAccessible(true);

			// Attempt to create an instance, expecting an IllegalStateException
			assertThrows(InvocationTargetException.class, constructor::newInstance);

			// Optional: You can inspect the specific cause of the exception
			Throwable exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
			assertEquals(IllegalStateException.class, exception.getCause().getClass());
			assertEquals(LotteryModuleConstant.MSG_UTIL_CLASS, exception.getCause().getMessage());
		} catch (NoSuchMethodException e) {
			fail("Private constructor not found");
		}
	}
}