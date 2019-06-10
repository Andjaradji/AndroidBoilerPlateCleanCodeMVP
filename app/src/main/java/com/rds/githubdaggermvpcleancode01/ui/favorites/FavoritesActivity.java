package com.rds.githubdaggermvpcleancode01.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.db.model.FavUser;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;
import com.rds.githubdaggermvpcleancode01.ui.user_detail.UserDetailActivity;
import com.rds.githubdaggermvpcleancode01.utils.OnItemClickListener;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

public class FavoritesActivity extends BaseActivity implements FavoritesView {
    ProgressBar progressBar;

    Toolbar toolbar;

    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    FavoritesPresenterContract favoritesPresenter;
    @Inject
    FavoritesAdapter favoritesAdapter;
    OnItemClickListener listener;
    private RecyclerView favUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent().inject(this);
        super.onCreate(savedInstanceState);

        renderView();
        init();

        favoritesPresenter.setView(this);
        favoritesPresenter.getFavoriteList();
    }


    private void renderView() {
        setContentView(R.layout.activity_favorites);
        favUserList = findViewById(R.id.rv_favorites_list);
        progressBar = findViewById(R.id.progress_bar);
        toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("List Favorite User");
    }

    private void init() {
        favUserList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void handleResult(Serializable userList) {
        List<FavUser> gitUserList = (List<FavUser>) userList;
        favoritesAdapter.setFavUserList(gitUserList);

        listener = new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                FavUser userFav = favoritesAdapter.getFavUserList().get(position);
//                Toast.makeText(getApplicationContext(), userFav.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FavoritesActivity.this, UserDetailActivity.class);
                intent.putExtra("username", userFav.getName());
                intent.putExtra("parent", "FavoritesActivity");
                intent.putExtra("id", userFav.getId());
                startActivity(intent);
            }
        };

        favoritesAdapter.setListener(listener);
        favUserList.setAdapter(favoritesAdapter);
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        favoritesPresenter.getFavoriteList();
//    }
}
