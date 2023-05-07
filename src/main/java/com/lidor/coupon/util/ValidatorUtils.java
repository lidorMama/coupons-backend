package com.lidor.coupon.util;

public class ValidatorUtils {
        public static boolean isNumberValid(String phone) {
        if (phone.length() < 1) {
            return false;
        }
        if (phone.length() > 10) {
            return false;
        }
        return true;
    }

    public static boolean isNameLengthValid(String name) {
        if (name.length() < 1) {
            return false;
        }
        if (name.length() > 45) {
            return false;
        }
        return true;
    }

    public static boolean isIdValid(Long id) {
        if (id == null) {
            return false;
        }
        if (id <= 0) {
            return false;
        }
        return true;
    }
}
