package com.example.hubson.randomuserapp.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.hubson.randomuserapp.repository.local.dao.UserDao;
import com.example.hubson.randomuserapp.repository.local.entity.User;
import com.example.hubson.randomuserapp.repository.remote.Webservice;
import com.example.hubson.randomuserapp.repository.remote.response_model.ApiResponse;
import com.example.hubson.randomuserapp.repository.remote.response_model.GetResponse;
import com.example.hubson.randomuserapp.utils.AppExecutors;

import java.util.List;

public class UserRepository {
    private static final Object LOCK = new Object();
    private static UserRepository sInstance;

    private final UserDao userDao;
    private final Webservice webservice;
    private final AppExecutors appExecutors;

    private static final int NUM_OF_RESULTS = 20;

    public UserRepository(UserDao userDao, Webservice webservice, AppExecutors appExecutors) {
        this.userDao = userDao;
        this.webservice = webservice;
        this.appExecutors = appExecutors;
    }

    public static UserRepository getInstance(UserDao userDao, Webservice webservice, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if(sInstance == null) {
                    sInstance = new UserRepository(userDao, webservice, executors);
                }
            }
        }
        return sInstance;
    }

    public LiveData<Resource<List<User>>> loadAllUsers() {
        return new NetworkBoundResource<List<User>, GetResponse>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull GetResponse item) {
                userDao.deleteAll();
                userDao.insertAll(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<User>> loadFromDb() {
                return userDao.loadAllUsers();
            }

            @Nullable
            @Override
            protected LiveData<ApiResponse<GetResponse>> createCall() {
                return webservice.getGraduates(NUM_OF_RESULTS);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<User>> loadUser(int id) {
        return new NetworkBoundResource<User, User>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull User item) {
                userDao.insert(item);
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.loadUserById(id);
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return false;
            }

            @Nullable
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return null;
            }
        }.getAsLiveData();
    }

    public void updateUser(User user) {
        appExecutors.diskIO().execute(() -> userDao.update(user));
    }
}
