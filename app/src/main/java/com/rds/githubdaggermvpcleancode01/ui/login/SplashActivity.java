package com.rds.githubdaggermvpcleancode01.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rds.githubdaggermvpcleancode01.ui.home.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent splashIntent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(splashIntent);
        finish();
    }
}
