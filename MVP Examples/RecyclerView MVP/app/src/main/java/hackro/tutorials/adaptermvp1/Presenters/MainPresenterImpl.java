package hackro.tutorials.adaptermvp1.Presenters;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import hackro.tutorials.adaptermvp1.Interactors.MainInteractorImpl;
import hackro.tutorials.adaptermvp1.Interfaces.MainInteractor;
import hackro.tutorials.adaptermvp1.Interfaces.MainPresenter;
import hackro.tutorials.adaptermvp1.Interfaces.MainView;
import hackro.tutorials.adaptermvp1.Models.Person;

/**
 * Created by david on 22/08/16.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView view;
    private MainInteractor interactor;


    public MainPresenterImpl(MainView view) {
        this.view = view;
        interactor = new MainInteractorImpl(this);

    }

    @Override
    public void initRecycler(List<Person> persons) {
        view.initRecycler(persons);
    }

    @Override
    public void loadListPerson() {
        interactor.initRecycler();
    }


}
