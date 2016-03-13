package com.tutorials.hackro.googlemaps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
        //Ejemplo Marcadores personalizados


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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lugar, 18));

        //Agregamos un marcado personalizado
        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_android))//Modificamos el icono,este recurso se encuentra en la carpeta mipmap
                .anchor(0.0f, 1.0f) // Indicamos el lugar donde se fijara: Izq -Inferior en este caso
                .position(lugar));//Indicamos la posicion o "lugar"

    }
    }



