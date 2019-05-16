package com.rds.githubdaggermvpcleancode01.callback;

import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;

public interface RequestCallback<T> {
    void onRequestSuccess(T t);

    void onRequestError(NetworkError networkError);


}