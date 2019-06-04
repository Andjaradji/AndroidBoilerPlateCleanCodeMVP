package com.rds.githubdaggermvpcleancode01.ui.login;

import com.google.firebase.auth.AuthResult;
import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.ui.base.BasePresenter;

import io.reactivex.disposables.Disposable;


public class LoginPresenter extends BasePresenter<LoginView, AuthResult> implements LoginPresenterContract {
    private final DataManager dataManager;


    public LoginPresenter(DataManager mDataManager) {
        super(mDataManager);
        this.dataManager = mDataManager;
    }

    @Override
    public void sendAppUserCredentials(String email, String password) {
        Disposable d = dataManager.firebaseLogin(this, email, password);
        mDisposables.add(d);
    }

    @Override
    public void onRequestError(NetworkError networkError) {
        super.onRequestError(networkError);
        mView.onFailure(networkError.getAppErrorMessage());
    }

    @Override
    public void onRequestSuccess(AuthResult authResult) {
        super.onRequestSuccess(authResult);
        if (mView != null) {
            mView.showSnackbar(authResult.getUser().getEmail() + " Login is successful");
            mView.handleResult(authResult);
        }
    }

    @Override
    public void onFirebaseAuthError(String errorMessage) {
        mView.showSnackbar(errorMessage);
    }

    @Override
    public void setView(LoginView view) {
        this.mView = view;
    }
}
