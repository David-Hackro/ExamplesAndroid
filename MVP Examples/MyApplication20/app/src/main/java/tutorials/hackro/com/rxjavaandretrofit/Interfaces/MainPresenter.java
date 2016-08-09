package tutorials.hackro.com.rxjavaandretrofit.Interfaces;

/**
 * Created by david on 9/08/16.
 */
public interface MainPresenter {

    void getAllData();
    void onSucess(String result);
    void onError(String error);


}
