package com.rds.githubdaggermvpcleancode01.ui.register;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;

import com.rds.githubdaggermvpcleancode01.ConstantGroup;
import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.network.model.RegisterResponse;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;
import com.rds.githubdaggermvpcleancode01.utils.UserValidationUtil;

import javax.inject.Inject;

public class RegisterActivity extends BaseActivity implements RegisterView, View.OnClickListener {
    TextInputEditText etName;
    TextInputEditText etEmail;
    TextInputEditText etPassword;

    Button btnRegister;

    @Inject
    RegisterPresenterContract registerPresenter;

    private RegisterResponse mRegisterResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        registerPresenter.setView(this);
        renderView();
    }


    private void renderView() {
        setContentView(R.layout.activity_register);
        etName = findViewById(R.id.et_reg_username);
        etEmail = findViewById(R.id.et_reg_email);
        etPassword = findViewById(R.id.et_reg_password);
        btnRegister = findViewById(R.id.btn_server_register);

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
        if (UserValidationUtil.validateEmpty(name, email, password)) {
            if (!UserValidationUtil.validateEmail(email)) {
                Snackbar.make(btnRegister, ConstantGroup.INCORRECT_EMAIL_FORMAT, Snackbar.LENGTH_LONG).show();
            } else if (!UserValidationUtil.validatePassword(password)) {
                Snackbar.make(btnRegister, ConstantGroup.INCORRECT_PASSWORD_FORMAT, Snackbar.LENGTH_LONG).show();
            } else {
                registerPresenter.registerUser(name, email, password);
            }
        } else {
            Snackbar.make(btnRegister, ConstantGroup.WARNING_EMPTY_FIELDS, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void handleResult(RegisterResponse response) {
        mRegisterResponse = response;
        Snackbar.make(btnRegister, mRegisterResponse.getName() + ConstantGroup.SUCCESSFUL_REGISTRATION, Snackbar.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure(String appErrorMessage) {
        Snackbar.make(btnRegister, appErrorMessage, Snackbar.LENGTH_LONG).show();
    }

//    private boolean validateEmpty(String name, String email, String password) {
//        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty();
//    }


}
