package com.example.david.mvp.Interfaces;

/**
 * Created by david on 3/03/16.
 */

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();
}