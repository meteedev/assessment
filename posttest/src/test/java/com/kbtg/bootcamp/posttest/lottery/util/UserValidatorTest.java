package com.kbtg.bootcamp.posttest.lottery.util;

import com.kbtg.bootcamp.posttest.exceptions.AppValidateException;
import org.junit.jupiter.api.Test;

import static com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant.MSG_INVALID_USER_FORMAT;
import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
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

}