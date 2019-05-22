package com.rds.githubdaggermvpcleancode01.ui.user_detail;

import com.rds.githubdaggermvpcleancode01.data.db.model.FavUser;

public interface UserDetailPresenterContract {
    void getUserDetail(String userName);

    void setView(UserDetailView view);

    void insertToFav(FavUser user);

    void removeFromFav(long id);

    FavUser checkUser(long id);
}
