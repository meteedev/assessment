package com.kbtg.bootcamp.posttest.lottery.constant;

public class LotteryModuleConstant {
    //common
    public static final String  MSG_DIGIT_ONLY = "Input digit only";
    public static final String  MSG_TICKET_NOT_FOUND = "Lottery id not found";
    public static final String  MSG_USER_TICKET_NOT_FOUND = "Transaction User buy this lottery id not found";


    // userId
    public static final Integer USER_ID_LENGTH = 10;
    public static final String  MSG_INVALID_USER_FORMAT = "User ID invalid format";

    // ticketId
    public static final Integer TICKET_LENGTH = 6;

    public static final String  MSG_INVALID_TICKET_FORMAT = "Lottery invalid format";
    public static final String MSG_AMOUNT_NOT_LESS_THAN_ZERO = "Amount must not be less than 0";


    //create lottery
    public static final String MSG_CREATE_TICKET_FAIL = "Failed to create Lottery";
    public static final String MSG_CREATE_TICKET_DUPILCATE = "Failed to create lottery duplicated lottery id";

    //buy lottery
    public static final Integer DEFAULT_PURCHASE_TICKET_AMOUNT = 1;
    public static final String MSG_PURCHASE_TICKET_AMOUNT_NOT_ENOUGH = "Lottery amount not enough for buy";
    public static final String MSG_PURCHASE_FAIL = "Failed to purchase Lottery";

    //sell back
    public static final String MSG_SELL_BACK_FAIL = "Failed to sell back Lottery";

}
