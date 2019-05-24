package com.rds.githubdaggermvpcleancode01.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;
import com.rds.githubdaggermvpcleancode01.ui.favorites.FavoritesActivity;
import com.rds.githubdaggermvpcleancode01.ui.login.LoginActivity;
import com.rds.githubdaggermvpcleancode01.ui.user_detail.UserDetailActivity;
import com.rds.githubdaggermvpcleancode01.utils.OnItemClickListener;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity implements HomeView {
    private RecyclerView githubUserList;

    private static final int REQUEST_CODE = 111;

    ProgressBar progressBar;

    @Inject
    HomePresenterContract homePresenter;

    @Inject
    HomeAdapter homeAdapter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    OnItemClickListener listener;
    private Menu menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent().inject(this);
        super.onCreate(savedInstanceState);
        launchLogin();
        renderView();
        init();

        homePresenter.setView(this);
        homePresenter.getUserList();


    }

    private void renderView(){
        setContentView(R.layout.activity_home);
        githubUserList = findViewById(R.id.rv_home_list);
        progressBar = findViewById(R.id.progress);
    }

    public void init(){
        githubUserList.setLayoutManager(linearLayoutManager);
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

    private void launchLogin() {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(loginIntent, REQUEST_CODE);
    }

    @Override
    public void handleResult(Serializable githubUsers) {
        List<GithubUser> gitUserList = (List<GithubUser>) githubUsers;
        homeAdapter.setUserList(gitUserList);

        listener = new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                GithubUser user = homeAdapter.getUserList().get(position);
                Toast.makeText(getApplicationContext(),user.getLogin(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, UserDetailActivity.class);
                intent.putExtra("username", user.getLogin());
                intent.putExtra("id", user.getId());
                startActivity(intent);
            }
        };

        homeAdapter.setListener(listener);
        githubUserList.setAdapter(homeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menuItem = menu;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("email");
                Snackbar.make(progressBar, result, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.see_fav_db) {
            Intent intent = new Intent(HomeActivity.this, FavoritesActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
