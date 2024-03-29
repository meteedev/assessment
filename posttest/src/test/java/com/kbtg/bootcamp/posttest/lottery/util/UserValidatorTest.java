package com.kbtg.bootcamp.posttest.lottery.util;

import com.kbtg.bootcamp.posttest.exceptions.AppValidateException;
import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant.MSG_INVALID_USER_FORMAT;
import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
	@Test
	void when_input_userId_null_Throws_AppValidateException(){
		try{
			UserValidator.validateUserIdFormat(null);
		}catch (AppValidateException e){
			assertEquals(MSG_INVALID_USER_FORMAT,e.getMessage());
		}
	}

	@Test
	void when_input_userId_not_digit_Throws_AppValidateException(){
		String userId = "U234567890";
		try{
			UserValidator.validateUserIdFormat(userId);
		}catch (AppValidateException e){
			assertEquals(MSG_INVALID_USER_FORMAT,e.getMessage());
		}
	}

	@Test
	void testPrivateConstructor() {
		// Use reflection to access the private constructor
		try {
			Constructor<UserValidator> constructor = UserValidator.class.getDeclaredConstructor();
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