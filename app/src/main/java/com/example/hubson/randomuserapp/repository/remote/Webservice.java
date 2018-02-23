package com.example.hubson.randomuserapp.repository.remote;

import android.arch.lifecycle.LiveData;

import com.example.hubson.randomuserapp.repository.remote.response_model.ApiResponse;
import com.example.hubson.randomuserapp.repository.remote.response_model.GetResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Webservice {

    @GET
    LiveData<ApiResponse<GetResponse>> getGraduates(@Query("results") int numOfResults);
}
