package com.rds.githubdaggermvpcleancode01.utils;

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


    public static boolean validateEmpty(String name, String email, String password) {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

}
