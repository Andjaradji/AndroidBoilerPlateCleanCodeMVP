package com.rds.githubdaggermvpcleancode01.ui.favorites;

public interface FavoritesPresenterContract {
    void getFavoriteList();

    void setView(FavoritesView view);
}
