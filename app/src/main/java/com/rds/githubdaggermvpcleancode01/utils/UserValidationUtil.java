package com.rds.githubdaggermvpcleancode01.utils;

import android.util.Patterns;

public class UserValidationUtil {

    public static boolean validateEmail(String email) {
        CharSequence inputString = email;

        if (inputString == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(inputString).matches();
    }


    public static boolean validatePassword(String password) {
        return password.length() >= 6;
    }


    public static boolean validateEmpty(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }


    public static boolean validateEmpty(String name, String email, String password, String phone) {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phone.isEmpty();
    }

    public static boolean validatePhone(String phoneNumber) {
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

}
