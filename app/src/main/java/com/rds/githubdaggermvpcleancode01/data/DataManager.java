package com.rds.githubdaggermvpcleancode01.data;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.rds.githubdaggermvpcleancode01.callback.RequestCallback;
import com.rds.githubdaggermvpcleancode01.data.db.AppDatabase;
import com.rds.githubdaggermvpcleancode01.data.db.model.FavUser;
import com.rds.githubdaggermvpcleancode01.data.network.AuthService;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkService;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginResponse;
import com.rds.githubdaggermvpcleancode01.data.network.model.RegisterResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseUser;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataManager {
    private final NetworkService mNetworkService;
    private final AuthService mAuthService;
    private final AppDatabase mAppDatabase;
    private final FirebaseAuth mFirebaseAuth;
    private final RxFirebaseAuth mRxFirebaseAuth;
    private Disposable disposable;
    private GithubUser user;
    private LoginResponse mLoginResponse;
    private RegisterResponse mRegResponse;

    private Serializable dataSerializable;

    @Inject
    public DataManager(NetworkService networkService, AuthService authService, AppDatabase appDatabase, FirebaseAuth firebaseAuth, RxFirebaseAuth rxFirebaseAuth) {
        this.mNetworkService = networkService;
        this.mAuthService = authService;
        this.mAppDatabase = appDatabase;
        this.mFirebaseAuth = firebaseAuth;
        this.mRxFirebaseAuth = rxFirebaseAuth;
    }


    public Disposable getDefaultUsers(final RequestCallback<Serializable> callback) {
        mNetworkService.getUsers(100,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GithubUser>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.beforeRequest();
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<GithubUser> githubUsers) {
                        dataSerializable = new ArrayList<>(githubUsers);
                    }


                    @Override
                    public void onError(Throwable throwable) {
                        callback.onRequestError(new NetworkError(throwable));
                        callback.afterRequest();
                    }

                    @Override
                    public void onComplete() {
                        callback.onRequestSuccess(dataSerializable);
                        callback.afterRequest();
                    }
                });

        return disposable;
    }

    public Disposable getUserDetailFromApi(final RequestCallback<Serializable> callback, String userName) {
        mNetworkService.getUserDetail(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GithubUser>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.beforeRequest();
                        disposable = d;
                    }

                    @Override
                    public void onNext(GithubUser githubUser) {
                        user = githubUser;
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onRequestError(new NetworkError(e));
                        callback.afterRequest();
                    }

                    @Override
                    public void onComplete() {
                        callback.onRequestSuccess(user);
                        callback.afterRequest();
                    }
                });

        return disposable;
    }

    public Disposable postLogin(final RequestCallback<LoginResponse> callback, String email, String password) {
        mAuthService.goLogin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(LoginResponse response) {
                        mLoginResponse = response;
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onRequestError(new NetworkError(e));
                    }

                    @Override
                    public void onComplete() {
                        callback.onRequestSuccess(mLoginResponse);
                    }
                });

        return disposable;
    }

    public Disposable postRegister(final RequestCallback<RegisterResponse> callback, String name, String email, String password) {
        mAuthService.doRegister(name, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(RegisterResponse response) {
                        mRegResponse = response;
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onRequestError(new NetworkError(e));
                    }

                    @Override
                    public void onComplete() {
                        callback.onRequestSuccess(mRegResponse);
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
//                        callback.beforeRequest();
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
//                        callback.onRequestDbError(new EmptyResultSetException(e.getMessage()));
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
//                        callback.beforeRequest();
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        callback.onUserFound(favUser[0]);
//                        callback.afterRequest();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onRequestError(new NetworkError(e));
//                        callback.afterRequest();
                    }
                });
    }

    public Disposable firebaseRegister(final RequestCallback<AuthResult> callback, String email, String password) {

        RxFirebaseAuth.createUserWithEmailAndPassword(mFirebaseAuth, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<AuthResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.beforeRequest();
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(AuthResult authResult) {
                        callback.onRequestSuccess(authResult);
                        callback.afterRequest();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFirebaseAuthError(e.getMessage());
                        callback.afterRequest();

                    }

                    @Override
                    public void onComplete() {
                        callback.afterRequest();
                    }
                });
        return disposable;
    }

    public Disposable firebaseLogin(final RequestCallback<AuthResult> callback, String email, String password) {
        RxFirebaseAuth.signInWithEmailAndPassword(mFirebaseAuth, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<AuthResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.beforeRequest();
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(AuthResult authResult) {
                        callback.onRequestSuccess(authResult);
                        callback.afterRequest();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFirebaseAuthError(e.getMessage());
                        callback.afterRequest();
                    }

                    @Override
                    public void onComplete() {
                        callback.afterRequest();
                    }
                });
        return disposable;
    }

    public Disposable firebaseUpdateUser(final RequestCallback<AuthResult> callback, FirebaseUser user, String username) {
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();
        RxFirebaseUser.updateProfile(user, changeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.beforeRequest();
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        callback.afterRequest();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFirebaseAuthError(e.getMessage());
                        callback.afterRequest();
                    }
                });
        return disposable;
    }


    public Disposable firebaseSendEmailVerification(final RequestCallback<AuthResult> callback, final FirebaseUser user) {
        RxFirebaseUser.sendEmailVerification(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.beforeRequest();
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        callback.onFirebaseCompleteable("A verification email has been sent to " + user.getEmail());
                        callback.afterRequest();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFirebaseAuthError(e.getMessage());
                        callback.beforeRequest();
                    }
                });
        return disposable;
    }


    public Disposable firebaseSendPasswordResetEmail(final RequestCallback<AuthResult> callback, final String email) {
        RxFirebaseAuth.sendPasswordResetEmail(mFirebaseAuth, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.beforeRequest();
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        callback.onFirebaseCompleteable("Go to " + email + " to reset your password");
                        callback.afterRequest();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFirebaseAuthError(e.getMessage());
                        callback.afterRequest();
                    }
                });
        return disposable;
    }


}
