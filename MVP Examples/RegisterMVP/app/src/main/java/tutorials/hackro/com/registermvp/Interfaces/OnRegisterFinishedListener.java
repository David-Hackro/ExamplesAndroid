package tutorials.hackro.com.registermvp.Interfaces;

/**
 * Created by david on 22/07/16.
 */
public interface OnRegisterFinishedListener {

    void setErrorUsername();
    void setErrorEmail();
    void setErrorPassword();
    void setErrorRepeatPassword();
    void setErrorEmptyFields();
    void onSucess();
}
