package com.rds.githubdaggermvpcleancode01.ui.user_detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;

import javax.inject.Inject;

public class UserDetailActivity extends BaseActivity implements UserDetailView {

    @Inject
    UserDetailPresenterContract userDetailPresenter;
    private GithubUser user;
    private ImageView imgUserProfile;
    private TextView txtUserNameProfile;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);

        renderView();

        Bundle extras = getIntent().getExtras();
        String userName = extras.getString("username");

        userDetailPresenter.setView(this);
        userDetailPresenter.getUserDetail(userName);

    }

    private void renderView() {
        setContentView(R.layout.activity_user_detail);
        imgUserProfile = findViewById(R.id.iv_user_big_pic);
        txtUserNameProfile = findViewById(R.id.tv_username_detail);
        progressBar = findViewById(R.id.progressbar);

    }

    @Override
    public void getUserDetailSuccess(GithubUser githubUser) {
        txtUserNameProfile.setText(githubUser.getLogin());

        Glide.with(this).load(githubUser.getAvatarUrl()).placeholder(android.R.drawable.ic_menu_gallery)
                .into(imgUserProfile);

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
}
