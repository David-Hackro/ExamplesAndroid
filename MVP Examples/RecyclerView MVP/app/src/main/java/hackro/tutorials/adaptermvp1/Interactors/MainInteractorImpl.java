package hackro.tutorials.adaptermvp1.Interactors;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hackro.tutorials.adaptermvp1.Adapter.PersonAdapter;
import hackro.tutorials.adaptermvp1.Interfaces.MainInteractor;
import hackro.tutorials.adaptermvp1.Interfaces.MainPresenter;
import hackro.tutorials.adaptermvp1.Models.Person;

/**
 * Created by david on 22/08/16.
 */

public class MainInteractorImpl implements MainInteractor{


    private MainPresenter presenter;

    public MainInteractorImpl(MainPresenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void initRecycler() {
        List<Person> personas = new ArrayList<Person>();

        personas.add(new Person("Sharon"));
        personas.add(new Person("Siri"));
        personas.add(new Person("Ari"));
        personas.add(new Person("Gael"));
        personas.add(new Person("Diego"));
        personas.add(new Person("Iker"));

        presenter.initRecycler(personas);
    }



}
