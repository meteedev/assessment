package com.kbtg.bootcamp.posttest.lottery.constant;

public interface LotteryModuleConstant {
    //common
    String MSG_DIGIT_ONLY = "Input digit only";
    String  MSG_TICKET_NOT_FOUND = "Lottery id not found";
    String  MSG_USER_TICKET_NOT_FOUND = "Transaction User buy this lottery id not found";


    // userId
    Integer USER_ID_LENGTH = 10;
    String  MSG_INVALID_USER_FORMAT = "User ID invalid format";

    // ticketId
    Integer TICKET_LENGTH = 6;
    String  MSG_INVALID_TICKET_FORMAT = "Lottery invalid format";
    String MSG_AMOUNT_NOT_LESS_THAN_ZERO = "Amount must not be less than 0";


    //create lottery
    String MSG_CREATE_TICKET_FAIL = "Failed to create Lottery";
    String MSG_CREATE_TICKET_DUPILCATE = "Failed to create lottery duplicated lottery id";

    //buy lottery
    Integer DEFAULT_PURCHASE_TICKET_AMOUNT = 1;
    String MSG_PURCHASE_TICKET_AMOUNT_NOT_ENOUGH = "Lottery amount not enough for buy";
    String MSG_PURCHASE_FAIL = "Failed to purchase Lottery";

    //sell back
    String MSG_SELL_BACK_FAIL = "Failed to sell back Lottery";

}
