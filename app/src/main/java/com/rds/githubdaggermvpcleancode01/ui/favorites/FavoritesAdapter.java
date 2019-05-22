package com.rds.githubdaggermvpcleancode01.ui.favorites;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rds.githubdaggermvpcleancode01.R;
import com.rds.githubdaggermvpcleancode01.data.db.model.FavUser;
import com.rds.githubdaggermvpcleancode01.utils.OnItemClickListener;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    protected OnItemClickListener listener;
    private Context context;
    private List<FavUser> favUserList;

    public FavoritesAdapter(Context context) {
        this.context = context;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public List<FavUser> getFavUserList() {
        return favUserList;
    }

    public void setFavUserList(List<FavUser> favUserList) {
        this.favUserList = favUserList;
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorites, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        FavUser user = favUserList.get(i);
        viewHolder.click(listener);

        viewHolder.txtUserName.setText(user.getName());
        Glide.with(context).load(user.getImage()).placeholder(android.R.drawable.ic_menu_gallery)
                .into(viewHolder.imgUserPic);
    }

    @Override
    public int getItemCount() {
        return favUserList == null ? 0 : favUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName;
        ImageView imgUserPic;

        public ViewHolder(View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.tv_fav_username);
            imgUserPic = itemView.findViewById(R.id.iv_fav_user_pic);
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
