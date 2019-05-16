package com.rds.githubdaggermvpcleancode01.ui.user_detail;

import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.ui.base.BasePresenter;

import io.reactivex.disposables.Disposable;

//public class UserDetailPresenter implements UserDetailPresenterContract {
//    private final DataManager dataManager;
//
//    private CompositeDisposable disposables;
//
//    private UserDetailView view;
//
//    public UserDetailPresenter(DataManager dataManager) {
//        this.dataManager = dataManager;
//        this.disposables = new CompositeDisposable();
//    }
//
//    @Override
//    public void getUserDetail(String userName) {
//        view.showLoading();
//        Disposable disposable = dataManager.getUserDetail(new RequestCallback<GithubUser>() {
//            @Override
//            public void beforeRequest() {
//                view.showLoading();
//            }
//
//            @Override
//            public void onRequestSuccess(GithubUser githubUser) {
//                view.hideLoading();
//                view.handleResult(githubUser);
//            }
//
//            @Override
//            public void onRequestError(NetworkError networkError) {
//                view.hideLoading();
//                view.onFailure(networkError.getAppErrorMessage());
//            }
//
//            @Override
//            public void requestComplete() {
//                view.hideLoading();
//            }
//
//        },userName);
//
//        disposables.add(disposable);
//
//    }
//
//    @Override
//    public void setView(UserDetailView view) {
//        this.view = view;
//    }
//}

public class UserDetailPresenter extends BasePresenter<UserDetailView, GithubUser> implements UserDetailPresenterContract {

    private final DataManager dataManager;

    public UserDetailPresenter(DataManager mDataManager) {
        super(mDataManager);
        this.dataManager = mDataManager;
    }


    @Override
    public void getUserDetail(String userName) {
        Disposable disposable = dataManager.getUserDetail(this, userName);
        mDisposables.add(disposable);
    }

    @Override
    public void onRequestSuccess(GithubUser githubUser) {
        super.onRequestSuccess(githubUser);
        if (mView != null) {
            mView.handleResult(githubUser);
            mView.hideLoading();
        }
    }

    @Override
    public void onRequestError(NetworkError networkError) {
        super.onRequestError(networkError);
        if (mView != null) {
            mView.onFailure(networkError.getAppErrorMessage());
            mView.hideLoading();
        }
    }


    @Override
    public void setView(UserDetailView view) {
        this.mView = view;
    }
}