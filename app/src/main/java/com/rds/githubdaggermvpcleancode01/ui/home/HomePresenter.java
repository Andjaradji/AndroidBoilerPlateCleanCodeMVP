package com.rds.githubdaggermvpcleancode01.ui.home;

import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class HomePresenter implements HomePresenterContract {

    private final DataManager dataManager;

    private HomeView view;

    private CompositeDisposable disposables;


    public HomePresenter(DataManager dataManager) {
        this.dataManager = dataManager;

        this.disposables = new CompositeDisposable();
    }

    @Override
    public void getUserList() {
        view.showLoading();

        Disposable disposable = dataManager.getDefaultUsers(new DataManager.GetUserListCallback() {
            @Override
            public void onSuccess(List<GithubUser> githubUsers) {
                view.hideLoading();
                view.getUserListSuccess(githubUsers);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                view.onFailure(networkError.getAppErrorMessage());
            }

        });

        disposables.add(disposable);
    }

    @Override
    public void setView(HomeView view) {
        this.view = view;
    }

    public  void onStop(){
        disposables.dispose();
    }
}
