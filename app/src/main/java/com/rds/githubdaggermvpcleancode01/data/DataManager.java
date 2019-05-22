package com.rds.githubdaggermvpcleancode01.data;

import com.rds.githubdaggermvpcleancode01.callback.RequestCallback;
import com.rds.githubdaggermvpcleancode01.data.db.AppDatabase;
import com.rds.githubdaggermvpcleancode01.data.db.model.FavUser;
import com.rds.githubdaggermvpcleancode01.data.network.AuthService;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkService;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginCredentials;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataManager {
    private final NetworkService mNetworkService;
    private final AuthService mAuthService;
    private final AppDatabase mAppDatabase;
    private Disposable disposable;
    private GithubUser user;
    private LoginResponse mResponse;

    private Serializable dataSerializable;
    //    private Serializable databaseSerializable;
//    private List<Serializable> listDataSerializable;
    private Single<FavUser> dmFavUser;

    @Inject
    public DataManager(NetworkService networkService, AuthService authService, AppDatabase appDatabase) {
        this.mNetworkService = networkService;
        this.mAuthService = authService;
        this.mAppDatabase = appDatabase;
    }


    public Disposable getDefaultUsers(final RequestCallback<Serializable> callback) {
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
                        dataSerializable = new ArrayList<>(githubUsers);
                    }


                    @Override
                    public void onError(Throwable throwable) {
                        callback.onRequestError(new NetworkError(throwable));
                    }

                    @Override
                    public void onComplete() {
                        callback.onRequestSuccess(dataSerializable);
                    }
                });

        return disposable;
    }

    public Disposable getUserDetail(final RequestCallback<Serializable> callback, String userName) {
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

    public void getAllFavoriteUsers(final RequestCallback<Serializable> callback) {
        mAppDatabase.userFavoriteModel().findAllFavoriteUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FavUser>>() {
                    @Override
                    public void accept(List<FavUser> userList) throws Exception {
                        dataSerializable = new ArrayList<>(userList);
                        callback.onRequestSuccess(dataSerializable);
                    }
                });
    }

//    public Disposable getAllFavoriteUsers(final RequestCallback<Serializable> callback) {
//        mAppDatabase.userFavoriteModel().findAllFavoriteUsers()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<List<FavUser>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        disposable = d;
//                    }
//
//                    @Override
//                    public void onSuccess(List<FavUser> userList) {
//                        dataSerializable = new ArrayList<>(userList);
//                        callback.onRequestSuccess(dataSerializable);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callback.onRequestError(new NetworkError(e));
//                    }
//                });
//
//        return disposable;
//    }

    public void addFavUser(final RequestCallback<Serializable> callback, final long favUserId, final String favUserName, final String favUserImageUrl) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                FavUser favUser = new FavUser(favUserId, favUserName, favUserImageUrl);
                mAppDatabase.userFavoriteModel().insertFavoriteUsers(favUser);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        callback.onUserAdded();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onRequestError(new NetworkError(e));
                    }
                });
    }

    public void deleteFavUser(final RequestCallback<Serializable> callback, final long id) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                FavUser favUser = mAppDatabase.userFavoriteModel().findUser(id);
                mAppDatabase.userFavoriteModel().deleteUser(favUser);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        callback.onUserRemoved();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onRequestError(new NetworkError(e));
                    }
                });
    }


    public void findFavUser(final RequestCallback<Serializable> callback, final long id) {
        final FavUser[] favUser = new FavUser[1];
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                favUser[0] = mAppDatabase.userFavoriteModel().findUser(id);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        callback.onUserFound(favUser[0]);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onRequestError(new NetworkError(e));
                    }
                });
    }

//    public void findFavUser(final RequestCallback<Serializable> callback, final long id){
//        Completable.fromAction(new Action() {
//            @Override
//            public void run() throws Exception {
//                mAppDatabase.userFavoriteModel().findUser(id);
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        disposable = d;
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        callback.onUserFound(id);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callback.onRequestError(new NetworkError(e));
//                    }
//                });
//    }


}
