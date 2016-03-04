package com.example.david.mvp.Interfaces;

/**
 * Created by david on 3/03/16.
 */


public interface LoginInteractor {
    void login(String username, String password, OnLoginFinishedListener listener);
}