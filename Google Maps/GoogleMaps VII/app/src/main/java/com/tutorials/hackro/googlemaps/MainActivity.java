package com.tutorials.hackro.googlemaps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
        //Ejemplo polilíneas
    /*
   Una polilínea es una lista de puntos,
   donde los segmentos de línea se dibujan entre puntos consecutivos.
        Tiene propiedades como:
            -Points
            -Width
            -Color
            etc
            Mas informacion: https://developers.google.com/android/reference/com/google/android/gms/maps/model/Polyline
     */


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
        LatLng lugar = new LatLng(19.3910038, -99.2837004);//creamos el punto de partida(DF)


       //Movemos la camara,El metodo newLatLngZoom esta sobrecargado
        //Este es uno de los metodos,y recibe una localizacion y el respectivo zoom como segundo parametro
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lugar, 20));


        // Indicamos los puntos para crear la polilinea
        //Ente este caso la ruta es del df - Guerrero - Michoacan y llegando a Guadalajara
        googleMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(17.547789, -99.532435))  // Guerrero
                .add(new LatLng(19.1539267, -103.0220045))  // Michoacan
                .add(new LatLng(20.6998812, -103.405454))  // Guadalajara
                );
        //Utilidad para generar polilineas de google
        //https://developers.google.com/maps/documentation/utilities/polylineutility
    }
    }



