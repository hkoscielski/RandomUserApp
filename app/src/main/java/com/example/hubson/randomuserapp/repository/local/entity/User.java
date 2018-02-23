package com.example.hubson.randomuserapp.repository.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String name;
    private final String gender;
    private final String phone;
    private final String email;

    @Embedded
    private final Location location;

    @ColumnInfo(name = "photo_url")
    private final String photoURL;

    @ColumnInfo(name = "is_favourite")
    private boolean isFavourite;

    public User(String name, String gender, String phone, String email, Location location, String photoURL) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.photoURL = photoURL;
        this.isFavourite = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Location getLocation() {
        return location;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public boolean isFavourite() {
        return isFavourite;
    }
}
