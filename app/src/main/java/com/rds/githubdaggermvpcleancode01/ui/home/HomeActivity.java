package com.rds.githubdaggermvpcleancode01.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rds.githubdaggermvpcleancode01.BaseApp;
import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;
import com.rds.githubdaggermvpcleancode01.utils.OnItemClickListener;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity implements HomeView {
    private RecyclerView githubUserList;

    ProgressBar progressBar;

    @Inject
    HomePresenterContract homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent().inject(this);
        super.onCreate(savedInstanceState);
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
        githubUserList.setLayoutManager(new LinearLayoutManager(this));
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
    public void getUserListSuccess(final List<GithubUser> githubUsers) {
        HomeAdapter homeAdapter = new HomeAdapter(BaseApp.get(this), githubUsers, new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                GithubUser user = githubUsers.get(position);
                Toast.makeText(getApplicationContext(),user.getLogin(),Toast.LENGTH_SHORT).show();
            }
        });
        githubUserList.setAdapter(homeAdapter);
    }
}
