package com.rds.githubdaggermvpcleancode01.ui.login;


import com.rds.githubdaggermvpcleancode01.data.network.model.LoginResponse;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseView;


public interface LoginView extends BaseView {
    void handleResult(LoginResponse response);
}
