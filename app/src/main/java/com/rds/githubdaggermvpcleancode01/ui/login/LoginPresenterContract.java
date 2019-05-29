package com.rds.githubdaggermvpcleancode01.ui.login;


public interface LoginPresenterContract {
    void sendAppUserCredentials(String email, String password);

    void setView(LoginView view);
}
