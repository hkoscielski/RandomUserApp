package com.example.hubson.randomuserapp.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.hubson.randomuserapp.repository.local.dao.UserDao;
import com.example.hubson.randomuserapp.repository.local.entity.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static UserDatabase Instance;

    public abstract UserDao getUserDao();

    public static UserDatabase getInstance(Context context) {
        if (Instance == null) {
            synchronized (LOCK) {
                if(Instance == null) {
                    Instance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), UserDatabase.class).build();
                }
            }
        }
        return Instance;
    }
}
