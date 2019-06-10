package com.rds.githubdaggermvpcleancode01.ui.register;

import com.google.firebase.auth.FirebaseUser;

public interface RegisterPresenterContract {
    void registerUser(String email, String password);

    void updateUser(FirebaseUser user, String username);

    void emailVerification(FirebaseUser user);

    void setView(RegisterView view);
}
