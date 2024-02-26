package com.kbtg.bootcamp.posttest.lottery.constant;

public interface LotteryModuleConstant {
	//common
	String  MSG_DIGIT_ONLY = "Input digit only";
	String  MSG_TICKET_NOT_FOUND = "Ticket not found in lottery table";

	String MSG_VIEW_TICKETS_NOT_FOUND  = "Not found lotteries";
	String  MSG_USER_TICKET_NOT_FOUND = "User Transaction not found";


	// userId
	Integer USER_ID_LENGTH = 10;
	String  MSG_INVALID_USER_FORMAT = "User ID invalid format";

	// ticketId
	Integer TICKET_LENGTH = 6;

	String  MSG_INVALID_TICKET_FORMAT = "Lottery invalid format";



	//create lottery
	String MSG_CREATE_TICKET_FAIL = "Failed to create Lottery";
	String MSG_CREATE_TICKET_DUPLICATE = "Failed to create lottery duplicated lottery id";



	//buy and  lottery
	Integer DEFAULT_PURCHASE_TICKET_AMOUNT = 1;
	String MSG_PURCHASE_TICKET_AMOUNT_NOT_ENOUGH = "Lottery amount not enough for buy";

	String MSG_PURCHASE_TICKET_NOT_FOUND_IN_MASTER_TABLE = "Ticket not found in lottery table";
	String MSG_PURCHASE_FAIL = "Failed to purchase Lottery";

	//sell back
	String MSG_SELL_BACK_FAIL = "Failed to sell back Lottery";

}
