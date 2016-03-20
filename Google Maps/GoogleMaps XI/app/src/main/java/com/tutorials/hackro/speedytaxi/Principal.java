package com.tutorials.hackro.speedytaxi;



import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tutorials.hackro.speedytaxi.Direction.DirectionResults;
import com.tutorials.hackro.speedytaxi.Direction.Route;
import com.tutorials.hackro.speedytaxi.Direction.RouteDecode;
import com.tutorials.hackro.speedytaxi.Direction.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class Principal extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
//Los puntos de localizacion estan en puebla,solo son de ejemplo.

    private String url = "https://maps.googleapis.com/maps/api/directions/";
    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFrag;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private boolean bPermissionGranted;
    private List<LatLng> list;
    private double latitud, longitud;
    private ImageView imagen;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private TextView tituloSeccion, or, des;
    private Utils utilidades;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getSupportActionBar().hide();  // for hiding
        prefs = getSharedPreferences("MIB", Context.MODE_PRIVATE);
        editor = prefs.edit();
        utilidades = new Utils();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bPermissionGranted = checkLocationPermission();
        }


        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (bPermissionGranted) {
                buildGoogleApiClient();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mGoogleMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }


        //MyItem current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        latitud = location.getLatitude();
        longitud = location.getLongitude();


        CameraPosition PosicionCamara = CameraPosition.builder()
                .target(latLng)//Direccion de la camara
                .zoom(20)//Zoom al mapa
                .bearing(90)//Dirección que la cámara está apuntando en, en grados en sentido horario desde el norte.
                .build();


        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(PosicionCamara),
                6000, null);
        //move map camera
        // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            new MyAsyncClass().execute();

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }


    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    buildGoogleApiClient();
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mGoogleMap.setMyLocationEnabled(true);


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    String responseJson = "";

//Peticion hilo secundario
private class MyAsyncClass extends AsyncTask<Void, Void, Void> {


    private DirectionResults directionResults;
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            directionResults = QueryPost(String.valueOf(latitud)+","+String.valueOf(longitud),"19.0187618,-98.2708821");
            Log.e("Thsi it's the ",responseJson);

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject json;
        try {
            list = new ArrayList<LatLng>();
            json = new JSONObject(responseJson);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes
                    .getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            list = utilidades.decodePoly(encodedString);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        success(directionResults);

    }
}




    public DirectionResults QueryPost(String origen,String destino) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GoogleDirection  service = retrofit.create(GoogleDirection.class);


        //Call<String> call = service.getDirection("19.0337567,-98.1888901","19.0187618,-98.2708821");
        Call<DirectionResults> call = service.getDirection(origen,destino);
         DirectionResults directionResults = call.execute().body();




        return  directionResults;
    }



    public void success(DirectionResults directionResults) {
        Log.i("zacharia", "inside on success" +directionResults.getRoutes().size());
        ArrayList<LatLng> routelist = new ArrayList<LatLng>();
        if(directionResults.getRoutes().size()>0){
            ArrayList<LatLng> decodelist;
            Route routeA = directionResults.getRoutes().get(0);
            Log.i("zacharia", "Legs length : "+routeA.getLegs().size());
            if(routeA.getLegs().size()>0){
                List<Steps> steps= routeA.getLegs().get(0).getSteps();
                Log.i("zacharia","Steps size :"+steps.size());
                Steps step;
                com.tutorials.hackro.speedytaxi.Direction.Location location;
                String polyline;
                for(int i=0 ; i<steps.size();i++){
                    step = steps.get(i);
                    location =step.getStart_location();
                    routelist.add(new LatLng(location.getLat(), location.getLng()));
                    Log.i("zacharia", "Start Location :" + location.getLat() + ", " + location.getLng());
                    polyline = step.getPolyline().getPoints();
                    decodelist = RouteDecode.decodePoly(polyline);
                    routelist.addAll(decodelist);
                    location =step.getEnd_location();
                    routelist.add(new LatLng(location.getLat() ,location.getLng()));
                    Log.i("zacharia","End Location :"+location.getLat() +", "+location.getLng());
                }
            }
        }
        Log.i("zacharia","routelist size : "+routelist.size());
        if(routelist.size()>0){
            PolylineOptions rectLine = new PolylineOptions().width(10).color(
                    Color.RED);

            for (int i = 0; i < routelist.size(); i++) {
                rectLine.add(routelist.get(i));
            }

            mGoogleMap.addPolyline(rectLine);


            // Adding route on the map

          /*  mMap.addPolyline(rectLine);
            markerOptions.position(toPosition);
            markerOptions.draggable(true);
            mMap.addMarker(markerOptions);*/
        }
    }


}