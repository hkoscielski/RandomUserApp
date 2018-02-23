package com.example.hubson.randomuserapp.repository.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.hubson.randomuserapp.repository.local.pojo.Location;
import com.example.hubson.randomuserapp.repository.local.pojo.Name;
import com.example.hubson.randomuserapp.repository.local.pojo.Picture;

@Entity(tableName = "Users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Embedded
    private final Name name;

    private final String gender;
    private final String phone;
    private final String email;

    @Embedded
    private final Location location;

    @Embedded
    private final Picture picture;

    @ColumnInfo(name = "is_favourite")
    private boolean isFavourite;

    public User(Name name, String gender, String phone, String email, Location location, Picture picture) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.picture = picture;
        this.isFavourite = false;
    }

    public int getId() {
        return id;
    }

    public Name getName() {
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

    public Picture getPicture() {
        return picture;
    }

    public boolean isFavourite() {
        return isFavourite;
    }
}
