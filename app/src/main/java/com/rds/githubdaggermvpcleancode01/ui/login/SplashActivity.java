package com.rds.githubdaggermvpcleancode01.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;
import com.rds.githubdaggermvpcleancode01.ui.home.HomeActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Intent splashIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(splashIntent);
        }

        finish();
    }
}
