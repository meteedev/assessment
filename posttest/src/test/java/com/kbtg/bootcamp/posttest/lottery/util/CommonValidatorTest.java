package com.kbtg.bootcamp.posttest.lottery.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonValidatorTest {

	@Test
	void input_not_digit_Throws_AppValidateException(){
		String userId = "U234567890";
		assertFalse(CommonValidator.isOnlyDigits(userId));

	}

}