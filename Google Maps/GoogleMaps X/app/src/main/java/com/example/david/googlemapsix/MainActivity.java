
package com.example.david.googlemapsix;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,ClusterManager.OnClusterInfoWindowClickListener{
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
    private ClusterManager<MyItem> mClusterManager;
    private TextView tituloSeccion, or, des;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();  // for hiding
        prefs = getSharedPreferences("MIB", Context.MODE_PRIVATE);
        editor = prefs.edit();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bPermissionGranted = checkLocationPermission();
        }


        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        setUpMapIfNeeded();
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


    private void setUpMapIfNeeded() {
        if (mGoogleMap != null) {
            return;
        }
        mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (mGoogleMap != null) {
            mClusterManager = new ClusterManager<MyItem>(this, mGoogleMap);
            mClusterManager.setRenderer(new OwnIconRendered(MainActivity.this, mGoogleMap, mClusterManager));


            /*
            mClusterManager.setOnClusterInfoWindowClickListener(new ClusterManager.OnClusterInfoWindowClickListener<MyItem>() {
                @Override
                public void onClusterInfoWindowClick(Cluster<MyItem> cluster) {
                    Toast.makeText(MainActivity.this,"csm",Toast.LENGTH_SHORT).show();
                    Log.e("Example"," Android");

                }
            });


            mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
                @Override
                public boolean onClusterClick(Cluster<MyItem> cluster) {
                    Toast.makeText(MainActivity.this,"csm",Toast.LENGTH_SHORT).show();
                    Log.e("ejemplo"," Android");
                    return false;
                }
            });

            mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<MyItem>() {
                @Override
                public void onClusterItemInfoWindowClick(MyItem myItem) {
                    Toast.makeText(MainActivity.this,"csm",Toast.LENGTH_SHORT).show();
                    Log.e("csm"," Android");
                }
            });

            mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
                @Override
                public boolean onClusterItemClick(MyItem myItem) {
                    Toast.makeText(MainActivity.this,"csm",Toast.LENGTH_SHORT).show();
                    Log.e("LMAO"," Android");
                    return false;
                }
            });
*/



            mGoogleMap.setOnCameraChangeListener(mClusterManager);


            mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(MainActivity.this,marker.getTitle(),Toast.LENGTH_SHORT).show();

                }
            });


            getList();



            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.0337592, -98.1869488), 1));
        }
    }

    @Override
    public void onClusterInfoWindowClick(Cluster cluster) {
        Toast.makeText(MainActivity.this,"asd",Toast.LENGTH_SHORT).show();
    }

    static class OwnIconRendered extends DefaultClusterRenderer<MyItem> {

        public OwnIconRendered(Context context, GoogleMap map,
                               ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.davidhackro);
            markerOptions.icon(icon);
            markerOptions.snippet(item.getTitle());
            markerOptions.title(item.getSubtitle());
            markerOptions.position(item.getPosition());


            super.onBeforeClusterItemRendered(item, markerOptions);
        }
    }


    public void getList() {
        MyItem item = new MyItem();

        item.setTitle("holi");
        item.setSubtitle("boli");
        item.setmPosition(new LatLng(19.0349408, -98.1849498));

        mClusterManager.addItem(item);





        MyItem item2 = new MyItem();
        item2.setTitle("abcde");
        item2.setSubtitle("6874514");
        item2.setmPosition(new LatLng(17.894147, -98.405813));


        mClusterManager.addItem(item2);

    }
}


