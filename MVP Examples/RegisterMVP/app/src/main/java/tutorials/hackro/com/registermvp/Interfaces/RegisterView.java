package tutorials.hackro.com.registermvp.Interfaces;

/**
 * Created by david on 22/07/16.
 */
public interface RegisterView {

    void showProgress();
    void hideProgress();
    void setErrorUserName();
    void setErrorEmail();
    void setErrorPassword();
    void setErrorRepeatPassword();
    void setErrorEmptyFields();
    void navigateToMain();

}
