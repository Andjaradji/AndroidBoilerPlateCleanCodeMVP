package com.rds.githubdaggermvpcleancode01.ui.user_detail;

import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class UserDetailPresenter implements UserDetailPresenterContract {
    private final DataManager dataManager;

    private CompositeDisposable disposables;

    private UserDetailView view;

    public UserDetailPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void getUserDetail(String userName) {
        view.showLoading();

        Disposable disposable = dataManager.getUserDetail(new DataManager.GetUserDetailCallback() {
            @Override
            public void onSuccess(GithubUser githubUser) {
                view.hideLoading();
                view.getUserDetailSuccess(githubUser);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                view.onFailure(networkError.getAppErrorMessage());
            }
        }, userName);

        disposables.add(disposable);

    }

    @Override
    public void setView(UserDetailView view) {
        this.view = view;
    }
}
