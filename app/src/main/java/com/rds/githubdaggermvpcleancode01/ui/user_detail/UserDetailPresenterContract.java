package com.rds.githubdaggermvpcleancode01.ui.user_detail;

public interface UserDetailPresenterContract {
    void getUserDetail(String userName);

    void setView(UserDetailView view);
}
