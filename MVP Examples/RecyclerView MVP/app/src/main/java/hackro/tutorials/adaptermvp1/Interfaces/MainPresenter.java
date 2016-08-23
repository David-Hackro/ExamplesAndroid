package hackro.tutorials.adaptermvp1.Interfaces;

import android.content.Context;

import java.util.List;

import hackro.tutorials.adaptermvp1.Models.Person;

/**
 * Created by david on 22/08/16.
 */

public interface MainPresenter {

    void initRecycler(List<Person> persons);
    void loadListPerson();

}
