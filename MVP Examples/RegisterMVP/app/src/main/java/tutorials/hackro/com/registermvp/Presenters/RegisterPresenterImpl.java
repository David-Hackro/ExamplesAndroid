package tutorials.hackro.com.registermvp.Presenters;

import tutorials.hackro.com.registermvp.Interactors.RegisterInteractorImpl;
import tutorials.hackro.com.registermvp.Interfaces.OnRegisterFinishedListener;
import tutorials.hackro.com.registermvp.Interfaces.RegisterInteractor;
import tutorials.hackro.com.registermvp.Interfaces.RegisterPresenter;
import tutorials.hackro.com.registermvp.Interfaces.RegisterView;

/**
 * Created by david on 22/07/16.
 */
public class RegisterPresenterImpl implements RegisterPresenter,OnRegisterFinishedListener {

    //View
    RegisterView registerView;
    RegisterInteractor registerInteractor;


    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        registerInteractor = new RegisterInteractorImpl();
    }

    @Override
    public void validateRegister(String username, String email, String password, String repeatPassoword) {
        if(registerView != null){
            registerView.showProgress();
        }
        registerInteractor.register(username,email,password,repeatPassoword,this);
    }

    @Override
    public void onDestroy() {
        if(registerView != null){
            registerView = null;
        }
    }

    @Override
    public void setErrorUsername() {
        if(registerView != null){
            registerView.setErrorUserName();
            registerView.hideProgress();
        }
    }

    @Override
    public void setErrorEmail() {
        if(registerView != null){
            registerView.setErrorEmail();
            registerView.hideProgress();
        }
    }

    @Override
    public void setErrorPassword() {
        if(registerView != null){
            registerView.setErrorPassword();
        }
    }

    @Override
    public void setErrorRepeatPassword() {
        if(registerView != null){
            registerView.setErrorRepeatPassword();
            registerView.hideProgress();
        }
    }

    @Override
    public void setErrorEmptyFields() {
        if(registerView != null){
            registerView.setErrorEmptyFields();
            registerView.hideProgress();
        }
    }

    @Override
    public void onSucess() {
        if(registerView != null){
            registerView.hideProgress();
            registerView.navigateToMain();
        }
    }
}
