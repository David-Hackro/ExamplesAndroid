package tutorials.hackro.com.rxjavaandretrofit.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import tutorials.hackro.com.rxjavaandretrofit.Interfaces.MainPresenter;
import tutorials.hackro.com.rxjavaandretrofit.Interfaces.MainView;
import tutorials.hackro.com.rxjavaandretrofit.Presenters.MainPresenterImpl;
import tutorials.hackro.com.rxjavaandretrofit.R;

public class MainActivity extends AppCompatActivity implements MainView{

    private ProgressBar progressBar;
    private TextView result;

    private MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar)findViewById(R.id.main_progress);
        result = (TextView)findViewById(R.id.main_result);

        presenter = new MainPresenterImpl(this);// send View -- MainView

        presenter.getAllData();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setData(String r) {
        this.result.setText(r);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }
}
