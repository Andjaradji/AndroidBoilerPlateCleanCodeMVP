package com.rds.githubdaggermvpcleancode01.ui.register;

import com.google.firebase.auth.AuthResult;
import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.ui.base.BasePresenter;

import io.reactivex.disposables.Disposable;

public class RegisterPresenter extends BasePresenter<RegisterView, AuthResult> implements RegisterPresenterContract {
    private final DataManager dataManager;

    public RegisterPresenter(DataManager mDataManager) {
        super(mDataManager);
        this.dataManager = mDataManager;
    }


    @Override
    public void registerUser(String email, String password) {
        Disposable d = dataManager.firebaseRegister(this, email, password);
        mDisposables.add(d);
    }

    @Override
    public void setView(RegisterView view) {
        this.mView = view;
    }


    @Override
    public void onRequestSuccess(AuthResult authResult) {
        super.onRequestSuccess(authResult);
        mView.showSnackbar(authResult.getUser().getEmail() + " Registration Successful");
        mView.handleResult(authResult);
    }


    @Override
    public void onFirebaseAuthError(String errorMessage) {
        mView.showSnackbar(errorMessage);
    }


}
