package com.rds.githubdaggermvpcleancode01.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.rds.githubdaggermvpcleancode01.data.db.model.FavUser;

import java.util.List;

import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite_users ORDER BY name ASC")
    Single<List<FavUser>> findAllFavoriteUsers();

    @Delete
    void deleteFavUsers(FavUser favUser);

    @Query("DELETE FROM favorite_users")
    void deleteAllFavUsers();

    @Insert(onConflict = REPLACE)
    long insertFavoriteUsers(FavUser user);

    @Update
    int updateFavUser(FavUser user);

    @Update
    void updateFavUser(List<FavUser> user);

    @Query("SELECT * FROM favorite_users WHERE id=:id")
    FavUser findUser(long id);

}
