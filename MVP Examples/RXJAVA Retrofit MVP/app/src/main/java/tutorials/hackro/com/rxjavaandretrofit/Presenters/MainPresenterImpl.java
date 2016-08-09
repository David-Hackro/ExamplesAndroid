package tutorials.hackro.com.rxjavaandretrofit.Presenters;

import tutorials.hackro.com.rxjavaandretrofit.Interactors.MainInteractorImpl;
import tutorials.hackro.com.rxjavaandretrofit.Interfaces.MainInteractor;
import tutorials.hackro.com.rxjavaandretrofit.Interfaces.MainPresenter;
import tutorials.hackro.com.rxjavaandretrofit.Interfaces.MainView;

/**
 * Created by david on 9/08/16.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView view;
    private MainInteractor interactor;

    public MainPresenterImpl(MainView view) {
        this.view = view;
        interactor = new MainInteractorImpl(this);

    }

    @Override
    public void getAllData() {
        if(view != null){
            view.showProgress();
            interactor.getAllData();
        }
    }

    @Override
    public void onSucess(String result) {
        if(view != null){
            view.setData(result);
            view.hideProgress();
        }
    }

    @Override
    public void onError(String error) {
        if(view != null){
            view.showError(error);
            view.hideProgress();
        }
    }
}
