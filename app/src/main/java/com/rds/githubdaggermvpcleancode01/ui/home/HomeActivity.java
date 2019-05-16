package com.rds.githubdaggermvpcleancode01.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.ui.base.BaseActivity;
import com.rds.githubdaggermvpcleancode01.ui.user_detail.UserDetailActivity;
import com.rds.githubdaggermvpcleancode01.utils.OnItemClickListener;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity implements HomeView {
    private RecyclerView githubUserList;

    ProgressBar progressBar;

    @Inject
    HomePresenterContract homePresenter;

    @Inject
    HomeAdapter homeAdapter;

    @Inject
    LinearLayoutManager linearLayoutManager;


    OnItemClickListener listener;

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


//    @Override
//    public void getUserListSuccess(final List<GithubUser> githubUsers) {
//        homeAdapter.setUserList(githubUsers);
//
//        listener = new OnItemClickListener() {
//            @Override
//            public void onClick(int position) {
//                GithubUser user = homeAdapter.getUserList().get(position);
//                Toast.makeText(getApplicationContext(),user.getLogin(),Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(HomeActivity.this, UserDetailActivity.class);
//                intent.putExtra("username", user.getLogin());
//                startActivity(intent);
//            }
//        };
//
//        homeAdapter.setListener(listener);
//
//        githubUserList.setAdapter(homeAdapter);
//    }


    @Override
    public void handleResult(List<GithubUser> githubUsers) {
        homeAdapter.setUserList(githubUsers);

        listener = new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                GithubUser user = homeAdapter.getUserList().get(position);
                Toast.makeText(getApplicationContext(),user.getLogin(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, UserDetailActivity.class);
                intent.putExtra("username", user.getLogin());
                startActivity(intent);
            }
        };

        homeAdapter.setListener(listener);

        githubUserList.setAdapter(homeAdapter);
    }
}
