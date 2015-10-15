package com.tutorials.hackro.sendemail;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tutorials.hackro.sendemail.Modelos.Email;

public class MainActivity extends AppCompatActivity {

    TextView emisor;
    EditText receptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emisor = (TextView)findViewById(R.id.textView2);
        emisor.setText(getEmail(this));
        receptor = (EditText)findViewById(R.id.editText);
    }


    private SendMail sendMail = new SendMail(this);
    public void Send(View v){
        sendMail.EnviarMail(new Email(getEmail(this),receptor.getText().toString(),"Este es el asunto","Este es un mensaje"));
    }

    ///Obtener cuenta de g mail ligada al dispositivo
    static String getEmail(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccounts();Account account = accounts[0];//getAccount(accountManager);

        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

}
