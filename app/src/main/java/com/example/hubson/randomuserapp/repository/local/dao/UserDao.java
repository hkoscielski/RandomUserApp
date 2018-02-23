package com.example.hubson.randomuserapp.repository.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.hubson.randomuserapp.repository.local.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM Users")
    LiveData<List<User>> loadAllUsers();

    @Query("SELECT * FROM Users WHERE id=:id")
    LiveData<User> loadUserById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Query("DELETE FROM Users")
    void deleteAll();
}
