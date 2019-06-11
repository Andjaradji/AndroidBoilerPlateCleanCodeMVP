package com.rds.githubdaggermvpcleancode01.ui.register;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
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
    public void updateUser(FirebaseUser user, String username) {
        Disposable d = dataManager.firebaseUpdateUser(this, user, username);
        mDisposables.add(d);
    }

    @Override
    public void emailVerification(FirebaseUser user) {
        Disposable d = dataManager.firebaseSendEmailVerification(this, user);
        mDisposables.add(d);
    }


    @Override
    public void setView(RegisterView view) {
        this.mView = view;
    }


    @Override
    public void onRequestSuccess(AuthResult authResult) {
        super.onRequestSuccess(authResult);
//        mView.showSnackbar(authResult.getUser().getEmail() + " Registration Successful");
        mView.handleResult(authResult);
    }


    @Override
    public void onFirebaseAuthError(String errorMessage) {
        mView.showSnackbar(errorMessage);
    }

    @Override
    public void onFirebaseCompleteable(String completeMessage) {
        mView.showSnackbar(completeMessage);
    }
}
