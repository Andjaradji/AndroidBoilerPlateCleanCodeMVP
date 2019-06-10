package com.rds.githubdaggermvpcleancode01.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.AuthResult;
import com.rds.githubdaggermvpcleancode01.ConstantGroup;
import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginResponse;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;
import com.rds.githubdaggermvpcleancode01.ui.home.HomeActivity;
import com.rds.githubdaggermvpcleancode01.ui.register.RegisterActivity;
import com.rds.githubdaggermvpcleancode01.utils.UserValidationUtil;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    Button btnServerLogin;
    RelativeLayout layoutRegister;

    TextView tvLoginTitle;
    ProgressBar progressBar;

    @Inject
    LoginPresenterContract loginPresenter;

    private LoginResponse mLoginResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        renderView();

        getIntentFromRegister();

        loginPresenter.setView(this);

    }

    private void getIntentFromRegister() {
        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");
        if (email != null) {
            Snackbar.make(btnServerLogin, email + " Successfully registered", Snackbar.LENGTH_LONG).show();
        }

    }

    private void renderView() {
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnServerLogin = findViewById(R.id.btn_server_login);
        tvLoginTitle = findViewById(R.id.tv_login_title);
        layoutRegister = findViewById(R.id.app_register_layout);
        progressBar = findViewById(R.id.progress_login);

        btnServerLogin.setOnClickListener(this);
        layoutRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_server_login:
                serverLogin();
                break;
            case R.id.app_register_layout:
                launchRegister();
                break;
        }

    }

    private void serverLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (UserValidationUtil.validateEmpty(email, password)) {
            if (!UserValidationUtil.validateEmail(email)) {
                Snackbar.make(btnServerLogin, ConstantGroup.INCORRECT_EMAIL_FORMAT, Snackbar.LENGTH_LONG).show();
            } else if (!UserValidationUtil.validatePassword(password)) {
                Snackbar.make(btnServerLogin, ConstantGroup.INCORRECT_PASSWORD_FORMAT, Snackbar.LENGTH_LONG).show();
            } else {
                loginPresenter.sendAppUserCredentials(email, password);
            }
        } else {
            Snackbar.make(btnServerLogin, ConstantGroup.WARNING_EMPTY_FIELDS, Snackbar.LENGTH_LONG).show();
        }

    }

    private void launchRegister() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
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
        Snackbar.make(btnServerLogin, appErrorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void handleResult(AuthResult authResult) {

        if (authResult.getUser().isEmailVerified()) {
            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
            homeIntent.putExtra("email", authResult.getUser().getEmail());
            startActivity(homeIntent);
            finish();

        } else {

            Snackbar.make(btnServerLogin, "Verify your email address", Snackbar.LENGTH_LONG).show();
        }



    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(btnServerLogin, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
