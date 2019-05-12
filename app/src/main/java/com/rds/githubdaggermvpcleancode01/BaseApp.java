package com.rds.githubdaggermvpcleancode01;

import android.app.Application;
import android.content.Context;

import com.rds.githubdaggermvpcleancode01.di.component.ApplicationComponent;
import com.rds.githubdaggermvpcleancode01.di.component.DaggerApplicationComponent;
import com.rds.githubdaggermvpcleancode01.di.module.ApplicationModule;
import com.rds.githubdaggermvpcleancode01.ui.home.HomeView;

public class BaseApp extends Application {
    private ApplicationComponent mApplicationComponent;



    @Override
    public void onCreate() {
        super.onCreate();
//        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        mApplicationComponent.inject(this);
    }

    public static BaseApp get (Context context){

        return (BaseApp) context.getApplicationContext();

    }

    public ApplicationComponent getComponent(){
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        this.mApplicationComponent = applicationComponent;
    }
}
