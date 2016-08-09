package tutorials.hackro.com.calculadoraexamplemvp.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import tutorials.hackro.com.calculadoraexamplemvp.Interfaces.IOperations;
import tutorials.hackro.com.calculadoraexamplemvp.Interfaces.MainPresenter;
import tutorials.hackro.com.calculadoraexamplemvp.Interfaces.MainView;
import tutorials.hackro.com.calculadoraexamplemvp.Presenters.MainPresenterImpl;
import tutorials.hackro.com.calculadoraexamplemvp.R;

public class MainActivity extends AppCompatActivity implements MainView, IOperations {
    @BindView(R.id.number1)
    EditText number1;
    @BindView(R.id.number2)
    EditText number2;
    @BindView(R.id.result)
    TextView result;
    //Ready! Go!

    //Presenter
    MainPresenter mainPresenter; //Interface

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
                            //class
        mainPresenter = new MainPresenterImpl(this);

    }

    @Override
    public void suma(View v) {
        mainPresenter.calcular(number1.getText().toString(),number2.getText().toString(),1);
    }

    @Override
    public void resta(View v) {
        mainPresenter.calcular(number1.getText().toString(),number2.getText().toString(),2);
    }

    @Override
    public void multiplicacion(View v) {
        mainPresenter.calcular(number1.getText().toString(),number2.getText().toString(),3);
    }

    @Override
    public void divicion(View v) {
        mainPresenter.calcular(number1.getText().toString(),number2.getText().toString(),4);
    }

    @Override
    public void setErrorFirstNumber() {
        number1.setError(getResources().getString(R.string.errorNumber));

    }

    @Override
    public void setErrorSecondNumber() {
        number2.setError(getResources().getString(R.string.errorNumber));

    }

    @Override
    public void showResult(String result) {
            this.result.setText(result);
    }

    @Override
    public void showError() {
        Toast.makeText(this,getResources().getString(R.string.errorOperation),Toast.LENGTH_SHORT).show();
    }
}
