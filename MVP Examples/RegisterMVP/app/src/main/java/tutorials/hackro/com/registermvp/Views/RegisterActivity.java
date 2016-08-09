package tutorials.hackro.com.registermvp.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import tutorials.hackro.com.registermvp.Interfaces.RegisterPresenter;
import tutorials.hackro.com.registermvp.Interfaces.RegisterView;
import tutorials.hackro.com.registermvp.Presenters.RegisterPresenterImpl;
import tutorials.hackro.com.registermvp.R;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    @BindView(R.id.register_username)
    EditText registerUsername;
    @BindView(R.id.register_email)
    EditText registerEmail;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_password2)
    EditText registerPassword2;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    //interface
    RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
                        //Class               //parameter View
        presenter = new RegisterPresenterImpl(this);

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
    public void setErrorUserName() {
        registerUsername.setError(getResources().getString(R.string.error_user_name));
    }

    @Override
    public void setErrorEmail() {
        registerEmail.setError(getResources().getString(R.string.error_invalid_email));
    }

    @Override
    public void setErrorPassword() {
        registerPassword.setError(getResources().getString(R.string.error_password));
    }

    @Override
    public void setErrorRepeatPassword() {
        registerPassword2.setError(getResources().getString(R.string.error_repeat_password));
    }

    @Override
    public void setErrorEmptyFields() {
        Toast.makeText(this,getResources().getString(R.string.error_empty_fields),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToMain() {
        startActivity(new Intent(this,MenuPrincipal.class));
    }


    //Others methods
    public void register(View v) {
        //send parameters
        //user,email,pass1,pass2
        presenter.validateRegister(registerUsername.getText().toString(),registerEmail.getText().toString(),registerPassword.getText().toString(),registerPassword2.getText().toString());
    }

    @Override
    protected void onDestroy()
    {
        presenter.onDestroy();
        super.onDestroy();
    }
}
