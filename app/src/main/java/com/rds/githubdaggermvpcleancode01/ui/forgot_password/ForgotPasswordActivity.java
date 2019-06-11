package com.rds.githubdaggermvpcleancode01.ui.forgot_password;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;
import com.rds.githubdaggermvpcleancode01.ui.login.LoginActivity;

import javax.inject.Inject;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordView {
    TextInputEditText etEmail;
    Button btnResetPassword;
    Toolbar toolbar;
    ProgressBar progressBar;


    @Inject
    ForgotPasswordPresenterContract forgotPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent().inject(this);
        super.onCreate(savedInstanceState);
        renderView();

        forgotPasswordPresenter.setView(this);

    }

    private void renderView() {
        setContentView(R.layout.activity_forgot_password);
        etEmail = findViewById(R.id.et_email_forgot_pw);
        btnResetPassword = findViewById(R.id.btn_reset_pw);
        toolbar = findViewById(R.id.my_toolbar);
        progressBar = findViewById(R.id.reset_pw_progress_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset Password");

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordPresenter.sendResetPassword(etEmail.getText().toString().trim());
            }
        });
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Snackbar.make(btnResetPassword, appErrorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(String successMessage) {
        Snackbar.make(btnResetPassword, successMessage, Snackbar.LENGTH_LONG).show();
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
