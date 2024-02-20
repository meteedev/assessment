package com.kbtg.bootcamp.posttest.lottery.util;

import com.kbtg.bootcamp.posttest.exceptions.AppValidateException;
import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;

import java.util.Objects;

public class TicketValidator {

    public static void validateTicketIdFormat(String ticketId){
        try {
            Objects.requireNonNull(ticketId, LotteryModuleConstant.MSG_INVALID_TICKET_FORMAT);
            if(ticketId.trim().length() != LotteryModuleConstant.TICKET_LENGTH){
                throw new AppValidateException(LotteryModuleConstant.MSG_INVALID_TICKET_FORMAT);
            }
        } catch (NullPointerException e) {
            throw new AppValidateException(LotteryModuleConstant.MSG_INVALID_TICKET_FORMAT);
        }
    }

}
