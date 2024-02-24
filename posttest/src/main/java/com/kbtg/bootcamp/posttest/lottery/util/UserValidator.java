package com.kbtg.bootcamp.posttest.lottery.util;

import com.kbtg.bootcamp.posttest.exceptions.AppValidateException;
import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;


public class UserValidator {
    public static void validateUserIdFormat(String userId){
        if (userId == null || userId.trim().length() != LotteryModuleConstant.USER_ID_LENGTH) {
            throw new AppValidateException(LotteryModuleConstant.MSG_INVALID_USER_FORMAT);
        }
    }

}
