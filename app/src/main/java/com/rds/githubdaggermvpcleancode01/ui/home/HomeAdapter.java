package com.rds.githubdaggermvpcleancode01.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;
import com.rds.githubdaggermvpcleancode01.utils.OnItemClickListener;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>  {
    protected OnItemClickListener listener;
    private List<GithubUser> userList;
    private Context context;

    public HomeAdapter(Context context) {
        this.context = context;
    }

//    public HomeAdapter(Context context, List<GithubUser> userList, OnItemClickListener listener) {
//        this.listener = listener;
//        this.userList = userList;
//        this.context = context;
//    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public List<GithubUser> getUserList() {
        return userList;
    }

    public void setUserList(List<GithubUser> userList) {
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GithubUser user = userList.get(i);
        viewHolder.click(listener);

        viewHolder.txtUserName.setText(user.getLogin());
        Glide.with(context).load(user.getAvatarUrl()).placeholder(android.R.drawable.ic_menu_gallery)
                .into(viewHolder.imgUserPic);
    }

    @Override
    public int getItemCount() {
        return userList == null? 0: userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName;
        ImageView imgUserPic;

        public ViewHolder(View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.tv_username);
            imgUserPic = itemView.findViewById(R.id.iv_user_pic);
        }

        public void click(final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
