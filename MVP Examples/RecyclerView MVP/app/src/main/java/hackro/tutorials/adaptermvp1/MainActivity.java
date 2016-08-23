package hackro.tutorials.adaptermvp1;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hackro.tutorials.adaptermvp1.Adapter.PersonAdapter;
import hackro.tutorials.adaptermvp1.Interfaces.MainPresenter;
import hackro.tutorials.adaptermvp1.Interfaces.MainView;
import hackro.tutorials.adaptermvp1.Interfaces.OnRecyclerViewItemClickListener;
import hackro.tutorials.adaptermvp1.Models.Person;
import hackro.tutorials.adaptermvp1.Presenters.MainPresenterImpl;

public class MainActivity extends AppCompatActivity implements MainView {


    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.RecyclerView)
    RecyclerView recyclerView;

    private PersonAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenterImpl(this);

        recyclerView.setHasFixedSize(true);
        //recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        adapter = new PersonAdapter(R.layout.card_people);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<Person>() {
            @Override
            public void onItemClick(View view, Person viewModel) {
                adapter.remove(viewModel);
            }
        });
        presenter.loadListPerson();
    }

    @Override
    public void initRecycler(List<Person> persons) {
        adapter.setListPerson(persons);
    }

}