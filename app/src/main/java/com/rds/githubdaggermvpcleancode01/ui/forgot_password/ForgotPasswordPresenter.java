package com.rds.githubdaggermvpcleancode01.ui.forgot_password;

import com.google.firebase.auth.AuthResult;
import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.ui.base.BasePresenter;

import io.reactivex.disposables.Disposable;

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordView, AuthResult> implements ForgotPasswordPresenterContract {
    private final DataManager dataManager;

    public ForgotPasswordPresenter(DataManager mDataManager) {
        super(mDataManager);
        this.dataManager = mDataManager;

    }

    @Override
    public void sendResetPassword(String email) {
        Disposable d = dataManager.firebaseSendPasswordResetEmail(this, email);
        mDisposables.add(d);

    }

    @Override
    public void onFirebaseAuthError(String errorMessage) {
        mView.onFailure(errorMessage);
    }

    @Override
    public void onFirebaseCompleteable(String completeMessage) {
        mView.onSuccess(completeMessage);
    }

    @Override
    public void setView(ForgotPasswordView view) {
        this.mView = view;
    }
}
