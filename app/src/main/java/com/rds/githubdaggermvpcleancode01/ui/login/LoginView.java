package com.rds.githubdaggermvpcleancode01.ui.login;


import com.google.firebase.auth.AuthResult;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseView;


public interface LoginView extends BaseView {
    void handleResult(AuthResult authResult);

    void showSnackbar(String message);
}
