package tutorials.hackro.com.rxjavaandretrofit.Interfaces;

/**
 * Created by david on 9/08/16.
 */
public interface MainView {

    void showProgress();

    void hideProgress();

    void setData(String result);

    void showError(String error);

}
