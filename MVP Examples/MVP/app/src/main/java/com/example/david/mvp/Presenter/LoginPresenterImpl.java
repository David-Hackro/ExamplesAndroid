package com.example.david.mvp.Presenter;

import com.example.david.mvp.Interactor.LoginInteractorImpl;
import com.example.david.mvp.Interfaces.LoginInteractor;
import com.example.david.mvp.Interfaces.LoginPresenter;
import com.example.david.mvp.Interfaces.LoginView;
import com.example.david.mvp.Interfaces.OnLoginFinishedListener;

/**
 * Created by david on 3/03/16.
 */

public class LoginPresenterImpl implements LoginPresenter, OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }

        loginInteractor.login(username, password, this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }
}


