package com.rds.githubdaggermvpcleancode01.ui.login;

import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginResponse;
import com.rds.githubdaggermvpcleancode01.ui.base.BasePresenter;

import io.reactivex.disposables.Disposable;


public class LoginPresenter extends BasePresenter<LoginView, LoginResponse> implements LoginPresenterContract {
    private final DataManager dataManager;


    public LoginPresenter(DataManager mDataManager) {
        super(mDataManager);
        this.dataManager = mDataManager;
    }

    @Override
    public void sendAppUserCredentials(String email, String password) {
        Disposable d = dataManager.postLogin(this, email, password);
        mDisposables.add(d);
    }

    @Override
    public void onRequestError(NetworkError networkError) {
        super.onRequestError(networkError);
        mView.onFailure(networkError.getAppErrorMessage());
        mView.hideLoading();
    }

    @Override
    public void onRequestSuccess(LoginResponse response) {
        super.onRequestSuccess(response);
        if (mView != null) {
            mView.handleResult(response);
            mView.hideLoading();
        }
    }

    @Override
    public void setView(LoginView view) {
        this.mView = view;
    }
}
