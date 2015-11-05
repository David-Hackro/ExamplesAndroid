package com.tutorials.hackro.facebook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends Activity {
    //Recuerda que estos valores cambian
    /*
    *Recuerda que debes crear esta app en facebook developers,deberas crear la app con la informacion de tu app,remplazando estas lineas por las tuyas
    * com.tutorials.hackro.facebook//tu paquete
    * com.tutorials.hackro.facebook.MainActivity//Tu activity donde se colocara facebook
    * */
    private TextView info;
    //Estos dos elementos,vienen de facebook(Agregado en el archivo buil.gradle)
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //--------------------------------------------//
        FacebookSdk.sdkInitialize(this);//El SDK necesita ser inicializado antes de usar cualquiera de sus métodos,pasando el contexto de la actividad(Activity)
        callbackManager = CallbackManager.Factory.create();//inizializamos el CallbackManager
        //---------------------------------------------//
        setContentView(R.layout.activity_main);

        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        //--------------------------------------------------//

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {//Si la autenticacion fue correcta
                info.setText("Login attempt success.");

            }

            @Override
            public void onCancel() {//Si se cancela la solicitus de login
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {//Si ocurre un error
                info.setText("Login attempt failed.");
            }
        });
    }

    //---------------------------------------------------------------------------//

    //---------------------------------------------------------------------------//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
