package com.rds.githubdaggermvpcleancode01.data;

import com.rds.githubdaggermvpcleancode01.callback.RequestCallback;
import com.rds.githubdaggermvpcleancode01.data.network.AuthService;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkService;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginCredentials;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DataManager {
    private final NetworkService mNetworkService;
    private final AuthService mAuthService;
    private Disposable disposable;
    private List<GithubUser> githubUserList;
    private GithubUser user;
    private LoginResponse mResponse;

    @Inject
    public DataManager(NetworkService networkService, AuthService authService) {
        this.mNetworkService = networkService;
        this.mAuthService = authService;
    }


    public Disposable getDefaultUsers(final RequestCallback<List<GithubUser>> callback) {
        mNetworkService.getUsers(100,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GithubUser>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<GithubUser> githubUsers) {
                        githubUserList = new ArrayList<>(githubUsers);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onRequestError(new NetworkError(throwable));
                    }

                    @Override
                    public void onComplete() {
                        callback.onRequestSuccess(githubUserList);
                    }
                });

        return disposable;
    }

    public Disposable getUserDetail(final RequestCallback<GithubUser> callback, String userName) {
        mNetworkService.getUserDetail(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GithubUser>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(GithubUser githubUser) {
                        user = githubUser;
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onRequestError(new NetworkError(e));
                    }

                    @Override
                    public void onComplete() {
                        callback.onRequestSuccess(user);
                    }
                });

        return disposable;
    }

    public Disposable postLogin(final RequestCallback<LoginResponse> callback, LoginCredentials credentials) {
        mAuthService.goLogin(credentials)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(LoginResponse response) {
                        mResponse = response;
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onRequestError(new NetworkError(e));
                    }

                    @Override
                    public void onComplete() {
                        callback.onRequestSuccess(mResponse);
                    }
                });
        return disposable;
    }


}
