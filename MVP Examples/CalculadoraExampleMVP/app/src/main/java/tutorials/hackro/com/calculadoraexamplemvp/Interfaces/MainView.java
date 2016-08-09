package tutorials.hackro.com.calculadoraexamplemvp.Interfaces;

/**
 * Created by david on 21/07/16.
 */
public interface MainView {

    void setErrorFirstNumber();
    void setErrorSecondNumber();
    void showResult(String result);
    void showError();
}
