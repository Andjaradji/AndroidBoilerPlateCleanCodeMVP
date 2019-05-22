package com.rds.githubdaggermvpcleancode01.ui.home;

import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.ui.base.BasePresenter;

import java.io.Serializable;

import io.reactivex.disposables.Disposable;

//public class HomePresenter implements HomePresenterContract {
//
//    private final DataManager dataManager;
//
//    private HomeView view;
//
//    private CompositeDisposable disposables;
//
//
//    public HomePresenter(DataManager dataManager) {
//        this.dataManager = dataManager;
//        this.disposables = new CompositeDisposable();
//    }
//
//    @Override
//    public void getUserList() {
//        view.showLoading();
//           Disposable disposable = dataManager.getDefaultUsers(new RequestCallback<List<GithubUser>>() {
//
//            @Override
//            public void onRequestSuccess(List<GithubUser> githubUsers) {
//                view.hideLoading();
//                view.handleResult(githubUsers);
//            }
//
//            @Override
//            public void onRequestError(NetworkError networkError) {
//                view.hideLoading();
//                view.onFailure(networkError.getAppErrorMessage());
//            }
//
//
//        });
//
//        disposables.add(disposable);
//    }
//
//    @Override
//    public void setView(HomeView view) {
//        this.view = view;
//    }
//
////
////    @Override
////    public void onResume() {
////
////    }
////
////    @Override
////    public void onDestroy() {
////        disposables.dispose();
////    }
//}

public class HomePresenter extends BasePresenter<HomeView, Serializable> implements HomePresenterContract {
    private final DataManager dataManager;

    public HomePresenter(DataManager mDatamanager) {
        super(mDatamanager);
        this.dataManager = mDatamanager;
    }


    @Override
    public void getUserList() {
        Disposable disposable = dataManager.getDefaultUsers(this);
        mDisposables.add(disposable);
    }

    @Override
    public void onRequestSuccess(Serializable githubUsers) {
        super.onRequestSuccess(githubUsers);
        if (mView != null) {
            mView.handleResult(githubUsers);
            mView.hideLoading();
        }

    }

    @Override
    public void onRequestError(NetworkError networkError) {
        super.onRequestError(networkError);
        mView.onFailure(networkError.getAppErrorMessage());
        mView.hideLoading();
    }


    @Override
    public void setView(HomeView view) {
        this.mView = view;
    }
}