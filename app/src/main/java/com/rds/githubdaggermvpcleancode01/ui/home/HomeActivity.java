package com.rds.githubdaggermvpcleancode01.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    Toolbar toolbar;

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

        String email = getIntent().getStringExtra("email");

        renderView();
        init();
        if (email != null) Snackbar.make(progressBar, email, Snackbar.LENGTH_LONG).show();
        homePresenter.setView(this);
        homePresenter.getUserList();

    }


    private void renderView() {
        setContentView(R.layout.activity_home);
        githubUserList = findViewById(R.id.rv_home_list);
        progressBar = findViewById(R.id.progress);
        toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getSupportActionBar().setTitle("Welcome " + user.getDisplayName());
    }

    public void init() {
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

//    private void launchLogin() {
//        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
//        startActivityForResult(loginIntent, REQUEST_CODE);
//    }

    @Override
    public void handleResult(Serializable githubUsers) {
        List<GithubUser> gitUserList = (List<GithubUser>) githubUsers;
        homeAdapter.setUserList(gitUserList);

        listener = new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                GithubUser user = homeAdapter.getUserList().get(position);
                Toast.makeText(getApplicationContext(), user.getLogin(), Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.see_fav_db:
                Intent intent = new Intent(HomeActivity.this, FavoritesActivity.class);
                startActivity(intent);
                break;
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
