package com.rds.githubdaggermvpcleancode01.callback;

import com.rds.githubdaggermvpcleancode01.data.network.NetworkError;

public interface RequestCallback<T> {
    void beforeRequest();

    void afterRequest();

    void onRequestSuccess(T t);

    void onRequestError(NetworkError networkError);

    void onFirebaseAuthError(String errorMessage);

    void onUserAdded();

    void onUserRemoved();

    void onUserFound(T t);


}
