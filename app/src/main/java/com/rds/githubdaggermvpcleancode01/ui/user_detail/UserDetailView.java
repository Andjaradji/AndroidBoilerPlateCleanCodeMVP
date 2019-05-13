package com.rds.githubdaggermvpcleancode01.ui.user_detail;

import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseView;

public interface UserDetailView extends BaseView {
    void getUserDetailSuccess(GithubUser githubUser);
}
