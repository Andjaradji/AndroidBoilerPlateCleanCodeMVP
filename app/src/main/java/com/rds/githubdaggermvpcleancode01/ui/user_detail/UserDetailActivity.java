package com.rds.githubdaggermvpcleancode01.ui.user_detail;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
    private ProgressBar progressBar;
    private Boolean isFavorite = false;

    private Menu menuItem;

    private FavUser favUser = new FavUser();
    private String parentClass = "";
    private long userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);

        renderView();

        Bundle extras = getIntent().getExtras();
        String userName = extras.getString("username");

        parentClass = extras.getString("parent");
        userId = extras.getLong("id");

        userDetailPresenter.setView(this);
        userDetailPresenter.getUserDetail(userName);
        favoriteState();
    }

    private void renderView() {
        setContentView(R.layout.activity_user_detail);
        imgUserProfile = findViewById(R.id.iv_user_big_pic);
        txtUserNameProfile = findViewById(R.id.tv_username_detail);
        progressBar = findViewById(R.id.progressbar);

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
//            if (data instanceof GithubUser) {
        GithubUser resultGithubUser = (GithubUser) data;
        String name = resultGithubUser.getLogin();
        String imgUrl = resultGithubUser.getAvatarUrl();
        long id = resultGithubUser.getId();
        txtUserNameProfile.setText(name);

        Glide.with(this).load(imgUrl).placeholder(android.R.drawable.ic_menu_gallery)
                .into(imgUserProfile);
        favUser.setName(name);
        favUser.setImage(imgUrl);
        favUser.setId(id);
//            }

//            else if (data instanceof FavUser) {
//                FavUser resultFavUser = (FavUser) data;
//                String name = resultFavUser.getName() + " from favorites";
//                String imgUrl = resultFavUser.getImage();
//                long id = resultFavUser.getId();
//                txtUserNameProfile.setText(name);
//
//                Glide.with(this).load(imgUrl).placeholder(android.R.drawable.ic_menu_gallery)
//                        .into(imgUserProfile);
//                favUser.setName(name);
//                favUser.setImage(imgUrl);
//                favUser.setUserId(id);
//
//            }
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(imgUserProfile, message, Snackbar.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addToFavID:
                if (isFavorite) {
                    userDetailPresenter.removeFromFav(userId);
                } else {
                    userDetailPresenter.insertToFav(favUser);
                }

                isFavorite = !isFavorite;
                setIsFavorite();
        }
        return super.onOptionsItemSelected(item);
    }


    private void favoriteState() {
        isFavorite = userDetailPresenter.checkUser(userId) != null;
    }

    private void setIsFavorite() {
        if (isFavorite) {
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites));
        } else {
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites));
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        isFavorite = userDetailPresenter.checkUser(userId) != null;
//    }
}
