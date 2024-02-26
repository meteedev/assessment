package com.kbtg.bootcamp.posttest.lottery.util;


import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;

public class CommonValidator {

	@SuppressWarnings("squid:S1128")
	private CommonValidator() {
		throw new IllegalStateException(LotteryModuleConstant.MSG_UTIL_CLASS);
	}

	public static boolean isOnlyDigits(String input) {
		// Use regular expression to check if the string contains only digits
		return input.matches("\\d+");
	}

}
