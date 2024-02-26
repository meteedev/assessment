package com.kbtg.bootcamp.posttest.lottery.util;

public class CommonValidator {
    public static boolean isOnlyDigits(String input) {
        // Use regular expression to check if the string contains only digits
        return input.matches("\\d+");
    }
}
