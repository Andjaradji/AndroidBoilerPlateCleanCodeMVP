package com.rds.githubdaggermvpcleancode01.di.component;

import android.app.Application;
import android.content.Context;

import com.rds.githubdaggermvpcleancode01.BaseApp;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkService;
import com.rds.githubdaggermvpcleancode01.di.ApplicationContext;
import com.rds.githubdaggermvpcleancode01.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseApp baseApp);

    @ApplicationContext
    Context context();

    Application application();

    NetworkService networkService();

}

