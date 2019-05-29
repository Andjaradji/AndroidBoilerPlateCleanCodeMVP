package com.rds.githubdaggermvpcleancode01.ui.home;

import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.ui.base.BasePresenter;

import java.io.Serializable;

import io.reactivex.disposables.Disposable;

public class HomePresenter extends BasePresenter<HomeView, Serializable> implements HomePresenterContract {
    private final DataManager dataManager;

    public HomePresenter(DataManager mDatamanager) {
        super(mDatamanager);
        this.dataManager = mDatamanager;
    }


    @Override
    public void getUserList() {
        Disposable disposable = dataManager.getDefaultUsers(this);
        mDisposables.add(disposable);
    }

    @Override
    public void onRequestSuccess(Serializable githubUsers) {
        super.onRequestSuccess(githubUsers);
        if (mView != null) {
            mView.handleResult(githubUsers);
        }

    }


    @Override
    public void onRequestError(NetworkError networkError) {
        super.onRequestError(networkError);
        mView.onFailure(networkError.getAppErrorMessage());
    }


    @Override
    public void setView(HomeView view) {
        this.mView = view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}