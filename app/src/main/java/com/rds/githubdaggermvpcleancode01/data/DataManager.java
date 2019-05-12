package com.rds.githubdaggermvpcleancode01.data;

import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;
import com.rds.githubdaggermvpcleancode01.data.network.NetworkService;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DataManager {
    private final NetworkService mNetworkService;
    private Disposable disposable;
    private List<GithubUser> githubUserList;

    @Inject
    public DataManager(NetworkService networkService) {
        this.mNetworkService = networkService;
    }

    public Disposable getDefaultUsers(final GetUserListCallback callback){
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
                        callback.onError(new NetworkError(throwable));
                    }

                    @Override
                    public void onComplete() {
                        callback.onSuccess(githubUserList);
                    }
                });

        return disposable;
    }






    public interface GetUserListCallback{
        void onSuccess(List<GithubUser> githubUserList);

        void onError(NetworkError networkError);
    }
}
