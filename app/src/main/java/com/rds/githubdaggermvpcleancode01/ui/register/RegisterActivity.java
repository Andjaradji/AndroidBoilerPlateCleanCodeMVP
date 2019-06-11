package com.rds.githubdaggermvpcleancode01.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.AuthResult;
import com.rds.githubdaggermvpcleancode01.ConstantGroup;
import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.network.model.RegisterResponse;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;
import com.rds.githubdaggermvpcleancode01.ui.login.LoginActivity;
import com.rds.githubdaggermvpcleancode01.utils.UserValidationUtil;

import javax.inject.Inject;

public class RegisterActivity extends BaseActivity implements RegisterView, View.OnClickListener {
    TextInputEditText etName;
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    TextInputEditText etPhone;
    Toolbar toolbar;

    ProgressBar progressBar;

    Button btnRegister;

    @Inject
    RegisterPresenterContract registerPresenter;

    private RegisterResponse mRegisterResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent().inject(this);
        super.onCreate(savedInstanceState);
        renderView();
        registerPresenter.setView(this);
    }


    private void renderView() {
        setContentView(R.layout.activity_register);
        etName = findViewById(R.id.et_reg_username);
        etEmail = findViewById(R.id.et_reg_email);
        etPassword = findViewById(R.id.et_reg_password);
        etPhone = findViewById(R.id.et_reg_phone);
        btnRegister = findViewById(R.id.btn_server_register);
        progressBar = findViewById(R.id.progress_register);
        toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.register_page);

        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_server_register:
                activateRegister();
                break;
            default:
                break;
        }

    }

    private void activateRegister() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        if (UserValidationUtil.validateEmpty(name, email, password, phone)) {
            if (!UserValidationUtil.validateEmail(email)) {
                Snackbar.make(btnRegister, ConstantGroup.INCORRECT_EMAIL_FORMAT, Snackbar.LENGTH_LONG).show();
            } else if (!UserValidationUtil.validatePassword(password)) {
                Snackbar.make(btnRegister, ConstantGroup.INCORRECT_PASSWORD_FORMAT, Snackbar.LENGTH_LONG).show();
            } else if (!UserValidationUtil.validatePhone(phone)) {
                Snackbar.make(btnRegister, ConstantGroup.INCORREXT_PHONE_NUMBER_FORMAT, Snackbar.LENGTH_LONG).show();
            } else {
                registerPresenter.registerUser(email, password);
            }
        } else {
            Snackbar.make(btnRegister, ConstantGroup.WARNING_EMPTY_FIELDS, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void handleResult(AuthResult authResult) {
        registerPresenter.updateUser(authResult.getUser(), etName.getText().toString().trim());
        registerPresenter.emailVerification(authResult.getUser());

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.putExtra("email", authResult.getUser().getEmail());
        intent.putExtra("name", authResult.getUser().getDisplayName());
        startActivity(intent);
        finish();
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

    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(btnRegister, message, Snackbar.LENGTH_LONG).show();
    }


}
