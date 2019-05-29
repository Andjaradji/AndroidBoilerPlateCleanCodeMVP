package com.rds.githubdaggermvpcleancode01.ui.base;

import android.support.v7.app.AppCompatActivity;

import com.rds.githubdaggermvpcleancode01.BaseApp;
import com.rds.githubdaggermvpcleancode01.di.component.ActivityComponent;
import com.rds.githubdaggermvpcleancode01.di.component.ApplicationComponent;
import com.rds.githubdaggermvpcleancode01.di.component.DaggerActivityComponent;
import com.rds.githubdaggermvpcleancode01.di.module.ActivityModule;

public abstract class BaseActivity<T extends BasePresenterContract> extends AppCompatActivity {
    protected T mPresenter;

    private ActivityComponent mActivityComponent;

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(BaseApp.get(this).getComponent())
                    .build();
        }

        return mActivityComponent;
    }


    protected ApplicationComponent applicationComponent() {
        return BaseApp.get(this).getComponent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }
}
