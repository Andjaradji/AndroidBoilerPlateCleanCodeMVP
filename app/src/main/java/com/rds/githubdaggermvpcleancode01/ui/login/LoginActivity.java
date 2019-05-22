package com.rds.githubdaggermvpcleancode01.ui.login;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginCredentials;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginResponse;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    Button btnServerLogin;

    TextView tvLoginTitle;

    LoginCredentials mUserCredentials;

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

        btnServerLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_server_login:
                serverLogin();
                break;
            case R.id.app_register_layout:
                break;
        }

    }

    private void serverLogin() {
        mUserCredentials = new LoginCredentials();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
//        mUserCredentials.setTitle("foo");
//        mUserCredentials.setBody("bar");
//        mUserCredentials.setId(1);
        mUserCredentials.setPassword(password);
        mUserCredentials.setEmail(email);
        loginPresenter.sendAppUserCredentials(mUserCredentials);
//            loginPresenter.sendAppUserCredentials("foo","bar",1);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure(String appErrorMessage) {
        tvLoginTitle.setText(appErrorMessage);
    }

    @Override
    public void handleResult(LoginResponse response) {
//                try {
//            Log.d("Handle Result", body.get("message").toString());
//        } catch (JSONException e) {
//            Log.d("Handle Result", e.getMessage());
//        }
//        try {
//            String title = body.getString("message");
//            tvLoginTitle.setText(title);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        mLoginResponse = response;

        tvLoginTitle.setText(mLoginResponse.toString());

//        Log.d("Coba Aja",mLoginResponse.getMessage());

    }


}
