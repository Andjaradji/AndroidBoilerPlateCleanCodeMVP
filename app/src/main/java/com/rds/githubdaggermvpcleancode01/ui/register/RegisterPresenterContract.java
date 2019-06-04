package com.rds.githubdaggermvpcleancode01.ui.register;

public interface RegisterPresenterContract {
    void registerUser(String email, String password);

    void setView(RegisterView view);
}
