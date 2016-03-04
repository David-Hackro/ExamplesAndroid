package com.example.david.mvp.Interfaces;

/**
 * Created by david on 3/03/16.
 */

public interface OnLoginFinishedListener {

    void onUsernameError();

    void onPasswordError();

    void onSuccess();
}