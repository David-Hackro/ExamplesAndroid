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
        //Ejemplo basico de google maps

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
        // Add a marker in Sydney, Australia, and move the camera.

        LatLng lugar = new LatLng(19.4112431, -99.2091994);//creamos una #localizacion#
        googleMap.addMarker(new MarkerOptions().position(lugar).title("Saludos desde Mexico"));//creamos una marca en el mapa "lugar"
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(lugar));//Movemos la camara en la localizacion "lugar" asignado

        //Formas de visualizar un mapa
        // MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID y  MAP_TYPE_NONE

        // googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);//Vista sin mapa
        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);//Vista Normal
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);//Vista Satelital
        //googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);//Vista Terreno
        //googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);//Vista Hibrida
    }


}

