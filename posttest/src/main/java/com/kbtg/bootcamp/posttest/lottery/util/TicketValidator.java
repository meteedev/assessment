package com.kbtg.bootcamp.posttest.lottery.util;

import com.kbtg.bootcamp.posttest.exceptions.AppValidateException;
import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;

import java.util.Objects;

public class TicketValidator {

    public static void validateTicketIdFormat(String ticketId){
        if (ticketId == null || ticketId.trim().length() != LotteryModuleConstant.TICKET_LENGTH) {
            throw new AppValidateException(LotteryModuleConstant.MSG_INVALID_TICKET_FORMAT);
        }
    }

}
