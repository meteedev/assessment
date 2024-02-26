package com.kbtg.bootcamp.posttest.lottery.util;

import com.kbtg.bootcamp.posttest.exceptions.AppValidateException;
import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;


public class TicketValidator {

	TicketValidator() {
		throw new IllegalStateException((LotteryModuleConstant.MSG_UTIL_CLASS));
	}

	public static void validateTicketIdFormat(String ticketId){
		if (ticketId == null
				|| ticketId.trim().length() != LotteryModuleConstant.TICKET_LENGTH
				|| !(CommonValidator.isOnlyDigits(ticketId))) {
			throw new AppValidateException(LotteryModuleConstant.MSG_INVALID_TICKET_FORMAT);
		}
	}





}
