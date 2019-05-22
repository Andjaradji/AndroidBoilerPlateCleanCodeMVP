package com.rds.githubdaggermvpcleancode01.data.db.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "favorite_users")
public class FavUser implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String image;
    private long userId;


    public FavUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }


    //    public FavUser(String name, String image) {
//        this.name = name;
//        this.image = image;
//    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
