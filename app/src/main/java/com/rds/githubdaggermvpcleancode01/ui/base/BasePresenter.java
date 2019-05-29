package com.rds.githubdaggermvpcleancode01.ui.base;


import com.rds.githubdaggermvpcleancode01.callback.RequestCallback;
import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<T extends BaseView, V> implements BasePresenterContract, RequestCallback<V> {
    protected CompositeDisposable mDisposables = new CompositeDisposable();
    protected T mView;
    protected DataManager mDataManager;


    public BasePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void beforeRequest() {
        if (mView != null) {
            mView.showLoading();
        }

    }

    @Override
    public void afterRequest() {
        if (mView != null) {
            mView.hideLoading();
        }
    }

    @Override
    public void onRequestSuccess(V v) {

    }

    @Override
    public void onRequestError(NetworkError networkError) {

    }

    @Override
    public void onUserAdded() {

    }

    @Override
    public void onUserRemoved() {

    }

    @Override
    public void onUserFound(V v) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        if (mDisposables != null) {
            mDisposables.dispose();
        }
        mView = null;
    }
}
