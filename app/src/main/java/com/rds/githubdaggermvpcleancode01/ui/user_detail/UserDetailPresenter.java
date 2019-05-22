package com.rds.githubdaggermvpcleancode01.ui.user_detail;

import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.data.db.model.FavUser;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.ui.base.BasePresenter;

import java.io.Serializable;

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

public class UserDetailPresenter extends BasePresenter<UserDetailView, Serializable> implements UserDetailPresenterContract {

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
    public void onRequestSuccess(Serializable data) {
        super.onRequestSuccess(data);
        if (mView != null) {
            mView.handleResult(data);
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

    @Override
    public void insertToFav(FavUser user) {
        long ids = dataManager.getDao().insertFavoriteUsers(user);
        mView.showSnackbar("User added to Database");
    }

    @Override
    public void removeFromFav(long id) {
        FavUser favUser = dataManager.getDao().findUser(id);

        dataManager.getDao().deleteFavUsers(favUser);
        mView.showSnackbar("User remove from Database");
    }

    @Override
    public FavUser checkUser(long id) {
        return dataManager.getDao().findUser(id);
//        Disposable disposable = dataManager.findFavUser(this, id);
//        mDisposables.add(disposable);
    }

}