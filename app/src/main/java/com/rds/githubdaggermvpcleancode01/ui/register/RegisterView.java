package com.rds.githubdaggermvpcleancode01.ui.register;

import com.google.firebase.auth.AuthResult;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseView;

public interface RegisterView extends BaseView {
    void handleResult(AuthResult authResult);

    void showSnackbar(String message);
}
