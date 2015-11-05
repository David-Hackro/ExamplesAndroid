package com.tutorials.hackro.googlemaps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
        //Ejemplo Mapas de interiores
        //Algunas construcciones muestran su interior

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);//Indicamos el contexto de la actividad actual
    }

    //Este metodo es implementado desde OnMapReadyCallback
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Creamos una localizacion
        LatLng lugar = new LatLng(19.4352007, -99.1550963);//creamos una #localizacion#


       //Movemos la camara,El metodo newLatLngZoom esta sobrecargado
        //Este es uno de los metodos,y recibe una localizacion y el respectivo zoom como segundo parametro
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lugar, 30));
    }


}

