package com.rds.githubdaggermvpcleancode01.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginResponse;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;
import com.rds.githubdaggermvpcleancode01.ui.register.RegisterActivity;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    Button btnServerLogin;
    RelativeLayout layoutRegister;

    TextView tvLoginTitle;

    @Inject
    LoginPresenterContract loginPresenter;

    private LoginResponse mLoginResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        loginPresenter.setView(this);
        renderView();
    }


    private void renderView() {
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnServerLogin = findViewById(R.id.btn_server_login);
        tvLoginTitle = findViewById(R.id.tv_login_title);
        layoutRegister = findViewById(R.id.app_register_layout);

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
        if (validateEmpty(email, password)) {
            loginPresenter.sendAppUserCredentials(email, password);
        } else {
            Snackbar.make(btnServerLogin, "Field(s) can not be empty", Snackbar.LENGTH_LONG).show();
        }

    }

    private void launchRegister() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure(String appErrorMessage) {
        Snackbar.make(btnServerLogin, appErrorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void handleResult(LoginResponse response) {
        mLoginResponse = response;
        if (response.getMessage().equals("Login successful")) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("email", mLoginResponse.getMessage());
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private boolean validateEmpty(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }


}
