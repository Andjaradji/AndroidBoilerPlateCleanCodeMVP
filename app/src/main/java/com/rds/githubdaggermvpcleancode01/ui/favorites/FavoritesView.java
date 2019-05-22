package com.rds.githubdaggermvpcleancode01.ui.favorites;


import com.rds.githubdaggermvpcleancode01.ui.base.BaseView;

import java.io.Serializable;


public interface FavoritesView extends BaseView {
    void handleResult(Serializable favUserList);
}
