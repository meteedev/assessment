package com.kbtg.bootcamp.posttest.lottery.util;

import com.kbtg.bootcamp.posttest.exceptions.AppValidateException;
import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;


public class UserValidator {
	private UserValidator() {
		throw new IllegalStateException((LotteryModuleConstant.MSG_UTIL_CLASS));
	}
	public static void validateUserIdFormat(String userId){
		if (userId == null
				|| userId.trim().length() != LotteryModuleConstant.USER_ID_LENGTH
				|| !(CommonValidator.isOnlyDigits(userId)))  {
			throw new AppValidateException(LotteryModuleConstant.MSG_INVALID_USER_FORMAT);
		}
	}


}


