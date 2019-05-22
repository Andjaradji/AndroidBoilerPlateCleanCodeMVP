package com.rds.githubdaggermvpcleancode01.di.module;

import android.app.Application;
import android.content.Context;

import com.rds.githubdaggermvpcleancode01.data.db.AppDatabase;
import com.rds.githubdaggermvpcleancode01.data.network.AuthService;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkService;
import com.rds.githubdaggermvpcleancode01.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    Application provideApplication (){
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return mApplication;
    }

    @Provides
    @Singleton
    NetworkService provideNetworkService(){
        return NetworkService.Factory.makeNetworkService(mApplication);
    }

    @Provides
    @Singleton
    AuthService provideAuthService() {
        return AuthService.Factory.makeLoginService(mApplication);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return AppDatabase.getDatabase(mApplication);
    }


}
