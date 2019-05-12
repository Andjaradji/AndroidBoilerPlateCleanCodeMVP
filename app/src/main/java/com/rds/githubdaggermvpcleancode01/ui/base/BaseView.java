package com.rds.githubdaggermvpcleancode01.ui.base;

public interface BaseView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

//    boolean isNetworkConnected();
}
