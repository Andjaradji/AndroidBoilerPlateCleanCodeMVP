package com.rds.githubdaggermvpcleancode01.ui.home;

import com.rds.githubdaggermvpcleancode01.ui.base.BaseView;

import java.io.Serializable;

public interface HomeView extends BaseView {
    void handleResult(Serializable githubUserList);

}
