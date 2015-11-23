package com.example.hackro.rijndael;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.bouncycastle.crypto.InvalidCipherTextException;

import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private  Metodos metodos;
    private EditText texto;
    private String key;
    private String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         key = "123456789abcdefrtyuioplaksbcgder";
        texto = (EditText)findViewById(R.id.Texto);
        metodos = new Metodos();


    }



    public void Sha512(View view) throws NoSuchAlgorithmException {
        mensaje = metodos.encriptar(texto.getText().toString());
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    public void Cifrado(View view) throws InvalidCipherTextException {
        mensaje =  metodos.testEncryptRijndael(texto.getText().toString(),key);
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();

    }

    public void Decifrado(View view) throws InvalidCipherTextException {
        mensaje =  metodos.testDecryptRijndael(mensaje, key);
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
}
