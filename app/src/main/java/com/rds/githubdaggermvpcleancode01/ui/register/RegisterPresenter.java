package com.rds.githubdaggermvpcleancode01.ui.register;

import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.data.network.model.RegisterResponse;
import com.rds.githubdaggermvpcleancode01.ui.base.BasePresenter;

import io.reactivex.disposables.Disposable;

public class RegisterPresenter extends BasePresenter<RegisterView, RegisterResponse> implements RegisterPresenterContract {
    private final DataManager dataManager;

    public RegisterPresenter(DataManager mDataManager) {
        super(mDataManager);
        this.dataManager = mDataManager;
    }

    @Override
    public void registerUser(String name, String email, String password) {
        Disposable d = dataManager.postRegister(this, name, email, password);
        mDisposables.add(d);
    }

    @Override
    public void setView(RegisterView view) {
        this.mView = view;
    }

    @Override
    public void onRequestError(NetworkError networkError) {
        super.onRequestError(networkError);
        mView.onFailure(networkError.getAppErrorMessage());
        mView.hideLoading();
    }

    @Override
    public void onRequestSuccess(RegisterResponse response) {
        super.onRequestSuccess(response);
        mView.handleResult(response);
        mView.hideLoading();

    }
}
