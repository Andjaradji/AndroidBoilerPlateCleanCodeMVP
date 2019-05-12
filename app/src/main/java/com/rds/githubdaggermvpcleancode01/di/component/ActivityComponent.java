package com.rds.githubdaggermvpcleancode01.di.component;

import com.rds.githubdaggermvpcleancode01.di.PerActivity;
import com.rds.githubdaggermvpcleancode01.di.module.ActivityModule;
import com.rds.githubdaggermvpcleancode01.ui.home.HomeActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject (HomeActivity homeActivity);
}