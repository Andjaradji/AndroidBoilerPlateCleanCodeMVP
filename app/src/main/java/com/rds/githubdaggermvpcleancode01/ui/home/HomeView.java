package com.rds.githubdaggermvpcleancode01.ui.home;

import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseView;

import java.util.List;

public interface HomeView extends BaseView {
    void handleResult(List<GithubUser> githubUserList);

}
