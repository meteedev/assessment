package com.kbtg.bootcamp.posttest.lottery.util;

import com.kbtg.bootcamp.posttest.exceptions.AppValidateException;
import com.kbtg.bootcamp.posttest.lottery.constant.LotteryModuleConstant;

import java.util.Objects;

public class UserValidator {
    public static void validateUserIdFormat(String userId){
        try {
            Objects.requireNonNull(userId, LotteryModuleConstant.MSG_INVALID_USER_FORMAT);
            if(userId.trim().length() != LotteryModuleConstant.USER_ID_LENGTH){
                throw new AppValidateException(LotteryModuleConstant.MSG_INVALID_USER_FORMAT);
            }
        } catch (NullPointerException e) {
            throw new AppValidateException(LotteryModuleConstant.MSG_INVALID_USER_FORMAT);
        }
    }

}
