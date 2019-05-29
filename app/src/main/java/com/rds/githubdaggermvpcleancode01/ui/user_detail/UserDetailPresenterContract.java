package com.rds.githubdaggermvpcleancode01.ui.user_detail;

public interface UserDetailPresenterContract {
    void getApiUserDetail(String userName);

    void setView(UserDetailView view);

    void insertToFav(long id, String name, String image);

    void removeFromFav(long id);

    void checkUser(long id);
}
