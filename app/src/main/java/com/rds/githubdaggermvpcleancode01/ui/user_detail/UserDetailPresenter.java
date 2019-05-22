package com.rds.githubdaggermvpcleancode01.ui.user_detail;

import com.rds.githubdaggermvpcleancode01.data.DataManager;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.ui.base.BasePresenter;

import java.io.Serializable;

import io.reactivex.disposables.Disposable;

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
    public void onUserAdded() {
        mView.showSnackbar("User Added to Database");
    }


    @Override
    public void onUserRemoved() {
        mView.showSnackbar("User Removed from Database");
    }



    @Override
    public void setView(UserDetailView view) {
        this.mView = view;
    }

    @Override
    public void insertToFav(long id, String name, String image) {
        dataManager.addFavUser(this, id, name, image);
    }

    @Override
    public void removeFromFav(long id) {
//        FavUser favUser = (FavUser) dataManager.findFavUser(this, id);
        dataManager.deleteFavUser(this, id);
    }

//    @Override
//    public FavUser checkUser(long id) {
//        return dataManager.getDao().findUser(id);
////        Disposable disposable = dataManager.findFavUser(this, id);
////        mDisposables.add(disposable);
//    }


    @Override
    public void onUserFound(Serializable data) {
        mView.checkUserInDb(data);
    }


    @Override
    public void checkUser(long id) {
        dataManager.findFavUser(this, id);
    }
}