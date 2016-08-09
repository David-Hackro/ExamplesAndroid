package com.example.david.mvp.Interfaces;

/**
 * Created by david on 3/03/16.
 */

public interface LoginPresenter {
    void validateCredentials(String username, String password);

    void onDestroy();
}