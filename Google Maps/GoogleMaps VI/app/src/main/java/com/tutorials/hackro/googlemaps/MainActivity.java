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

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
        //Ejemplo Marcadores planos-Efecto giratorio


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
        // Marcadores planas girarán cuando el mapa se gira,
        //Y el cambio en perspectiva cuando el mapa se inclina..
        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_android))//Modificamos el icono,este recurso se encuentra en la carpeta mipmap
                .position(lugar)//Indicamos la posicion
                .flat(true)
                .rotation(360));//Rotacion

        //Mas parametros aqui: https://developers.google.com/android/reference/com/google/android/gms/maps/model/CameraPosition
        CameraPosition PosicionCamara = CameraPosition.builder()
                .target(lugar)//Direccion de la camara
                .zoom(20)//Zoom al mapa
                .bearing(90)//Dirección que la cámara está apuntando en, en grados en sentido horario desde el norte.
                .build();


        //Mas informacion de animateCamera:https://developers.google.com/android/reference/com/google/android/gms/maps/GoogleMap
        // Animar el cambio en la vista de la cámara en  9 segundos
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        PosicionCamara),//Indicamos lka posicion de la camara
                         9000,//Indicamos el tiempo que tardara en rotar(9 segundos)
                            null);

    }
    }



