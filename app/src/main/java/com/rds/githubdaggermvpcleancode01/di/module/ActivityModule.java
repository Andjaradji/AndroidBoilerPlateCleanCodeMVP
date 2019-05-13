package com.rds.githubdaggermvpcleancode01.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.di.ActivityContext;
import com.rds.githubdaggermvpcleancode01.ui.home.HomeAdapter;
import com.rds.githubdaggermvpcleancode01.ui.home.HomePresenter;
import com.rds.githubdaggermvpcleancode01.ui.home.HomePresenterContract;
import com.rds.githubdaggermvpcleancode01.ui.user_detail.UserDetailPresenter;
import com.rds.githubdaggermvpcleancode01.ui.user_detail.UserDetailPresenterContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }

    @Provides
    HomePresenterContract provideHomePresenter(DataManager dataManager){
        return new HomePresenter(dataManager);
    }

    @Provides
    UserDetailPresenterContract provideUserDetailPresenter(DataManager dataManager) {
        return new UserDetailPresenter(dataManager);
    }


    @Provides
    HomeAdapter provideHomeAdapter() {
        return new HomeAdapter(mActivity);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(mActivity);
    }



}
