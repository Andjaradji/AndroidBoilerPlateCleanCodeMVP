package com.rds.githubdaggermvpcleancode01.ui.register;

import com.rds.githubdaggermvpcleancode01.data.network.model.RegisterResponse;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseView;

public interface RegisterView extends BaseView {
    void handleResult(RegisterResponse response);
}
