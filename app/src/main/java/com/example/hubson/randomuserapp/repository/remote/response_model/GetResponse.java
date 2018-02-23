package com.example.hubson.randomuserapp.repository.remote.response_model;

import com.example.hubson.randomuserapp.repository.local.entity.User;

import java.util.List;

public class GetResponse {
    private List<User> results;

    public List<User> getResults() {
        return results;
    }

    public void setResults(List<User> results) {
        this.results = results;
    }
}
