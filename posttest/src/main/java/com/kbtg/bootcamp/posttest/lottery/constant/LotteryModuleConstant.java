package com.kbtg.bootcamp.posttest.lottery.constant;

public interface LotteryModuleConstant {

    Integer USER_ID_LENGTH = 10;
    String  MSG_INVALID_USER_ID = "User ID invalid format";

    Integer TICKET_LENGTH = 6;
    String  MSG_INVALID_TICKET_ID = "Lottery Length must be 6 characters";
    String MSG_DIGIT_ONLY = "";

    String MSG_AMOUNT_NOT_LESS_THAN_ZERO = "Amount must not be less than 0";

}
