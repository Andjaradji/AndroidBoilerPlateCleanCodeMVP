package com.rds.githubdaggermvpcleancode01.ui.user_detail;

import com.rds.githubdaggermvpcleancode01.ui.base.BaseView;

import java.io.Serializable;

public interface UserDetailView extends BaseView {
    void handleResult(Serializable githubUser);

//    void checkDb(Serializable favUser);

    void showSnackbar(String message);

    void checkUserInDb(Serializable data);
}
