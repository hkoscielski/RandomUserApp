package com.example.hubson.randomuserapp.repository.remote;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebserviceImpl {
    private static final Object LOCK = new Object();
    private static Webservice sInstance;

    private WebserviceImpl() {}

    public static Webservice getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if(sInstance == null) {
                    sInstance = new Retrofit.Builder()
                            .baseUrl(ApiConstants.RANDOM_USER_API_URL)
                            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                            .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                            .build().create(Webservice.class);
                }
            }
        }
        return sInstance;
    }
}
