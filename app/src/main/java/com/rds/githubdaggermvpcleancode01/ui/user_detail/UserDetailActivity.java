package com.rds.githubdaggermvpcleancode01.ui.user_detail;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.db.model.FavUser;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;

import java.io.Serializable;

import javax.inject.Inject;

public class UserDetailActivity extends BaseActivity implements UserDetailView {

    @Inject
    UserDetailPresenterContract userDetailPresenter;
    private ImageView imgUserProfile;
    private TextView txtUserNameProfile;
    private ProgressBar userDetailProgressBar;
    private Boolean isFavorite = false;

    private Toolbar toolbar;

    private Menu menuItem;

    private long userId;
    private String favUserName;
    private String favUserImageUrl;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent().inject(this);
        super.onCreate(savedInstanceState);
        userDetailPresenter.setView(this);

        renderView();

        Bundle extras = getIntent().getExtras();
        userName = extras.getString("username");

        userId = extras.getLong("id");

        userDetailPresenter.checkUser(userId);
        getSupportActionBar().setTitle(userName);
    }

    private void renderView() {
        setContentView(R.layout.activity_user_detail);
        imgUserProfile = findViewById(R.id.iv_user_big_pic);
        txtUserNameProfile = findViewById(R.id.tv_username_detail);
        userDetailProgressBar = findViewById(R.id.progress_bar);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_detail_menu, menu);
        menuItem = menu;
        setIsFavorite();
        return true;
    }

    @Override
    public void handleResult(Serializable data) {
        GithubUser resultGithubUser = (GithubUser) data;
        favUserName = resultGithubUser.getLogin();
        favUserImageUrl = resultGithubUser.getAvatarUrl();
        userId = resultGithubUser.getId();

        txtUserNameProfile.setText(favUserName);
        Glide.with(this).load(favUserImageUrl).into(imgUserProfile);
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(imgUserProfile, message, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void showLoading() {
        userDetailProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        userDetailProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addToFavID:
                if (isFavorite) {
                    userDetailPresenter.removeFromFav(userId);
                } else {
                    userDetailPresenter.insertToFav(userId, favUserName, favUserImageUrl);
                }

                isFavorite = !isFavorite;
                setIsFavorite();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void checkUserInDb(Serializable data) {
        isFavorite = data != null;
        if (!isFavorite) {
            userDetailPresenter.getApiUserDetail(userName);
        } else {
            FavUser user = (FavUser) data;
            favUserName = user.getName() + " Taken from db";
            favUserImageUrl = user.getImage();
            userId = user.getId();

            txtUserNameProfile.setText(favUserName);
            Glide.with(this).load(favUserImageUrl).into(imgUserProfile);
        }
    }

    private void setIsFavorite() {
        if (isFavorite) {
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites));
        } else {
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites));
        }
    }

}
