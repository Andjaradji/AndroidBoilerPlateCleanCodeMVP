package com.rds.githubdaggermvpcleancode01.ui.forgot_password;

public interface ForgotPasswordPresenterContract {
    void sendResetPassword(String email);

    void setView(ForgotPasswordView view);
}
