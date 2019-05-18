package com.rds.githubdaggermvpcleancode01.ui.login;


import com.rds.githubdaggermvpcleancode01.data.network.model.LoginCredentials;

public interface LoginPresenterContract {
    void sendAppUserCredentials(LoginCredentials credentials);

    void setView(LoginView view);
}
