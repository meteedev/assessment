package com.kbtg.bootcamp.posttest.lottery.util;

import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class CommonValidatorTest {

	@Test
	void input_not_digit_Throws_AppValidateException(){
		String userId = "U234567890";
		assertFalse(CommonValidator.isOnlyDigits(userId));

	}

	@Test
	void testPrivateConstructor() {
		// Use reflection to access the private constructor
		try {
			Constructor<CommonValidator> constructor = CommonValidator.class.getDeclaredConstructor();
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