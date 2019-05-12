package com.rds.githubdaggermvpcleancode01.di.module;

import android.app.Activity;
import android.content.Context;

import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.di.ActivityContext;
import com.rds.githubdaggermvpcleancode01.ui.home.HomeAdapter;
import com.rds.githubdaggermvpcleancode01.ui.home.HomePresenter;
import com.rds.githubdaggermvpcleancode01.ui.home.HomePresenterContract;

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
    HomeAdapter provideHomeAdapter() {
        return new HomeAdapter(mActivity);
    }



}
