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
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
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

            mGoogleMap.setOnCameraChangeListener(mClusterManager);



            mClusterManager.addItem(new MyItem(17.894167,-98.405823));
            mClusterManager.addItem(new MyItem(17.894167,-98.405813));
            mClusterManager.addItem(new MyItem(17.894167,-98.405803));
            mClusterManager.addItem(new MyItem(17.915001,-98.423046));
            mClusterManager.addItem(new MyItem(17.915001,-98.423036));
            mClusterManager.addItem(new MyItem(17.915001,-98.423026));
            mClusterManager.addItem(new MyItem(17.920001,-97.922501));
            mClusterManager.addItem(new MyItem(17.920001,-97.922502));
            mClusterManager.addItem(new MyItem(17.946111,-98.269712));
            mClusterManager.addItem(new MyItem(17.946111,-98.269702));
            mClusterManager.addItem(new MyItem(17.946111,-98.269692));
            mClusterManager.addItem(new MyItem(17.946111,-98.269682));
            mClusterManager.addItem(new MyItem(17.961389,-98.662223));
            mClusterManager.addItem(new MyItem(17.961389,-98.662224));
            mClusterManager.addItem(new MyItem(17.961944,-98.618323));
            mClusterManager.addItem(new MyItem(17.961944,-98.618313));
            mClusterManager.addItem(new MyItem(17.961944,-98.618303));
            mClusterManager.addItem(new MyItem(17.971667,-97.862212));
            mClusterManager.addItem(new MyItem(17.971667,-97.862202));
            mClusterManager.addItem(new MyItem(17.971667,-97.862192));
            mClusterManager.addItem(new MyItem(17.971667,-97.862182));
            mClusterManager.addItem(new MyItem(17.972501,-98.320278));
            mClusterManager.addItem(new MyItem(17.976871,-98.708889));
            mClusterManager.addItem(new MyItem(17.976871,-98.708891));
            mClusterManager.addItem(new MyItem(17.978333,-98.386379));
            mClusterManager.addItem(new MyItem(17.978333,-98.386369));
            mClusterManager.addItem(new MyItem(17.978333,-98.386359));
            mClusterManager.addItem(new MyItem(17.978611,-97.921101));
            mClusterManager.addItem(new MyItem(17.978611,-97.921071));
            mClusterManager.addItem(new MyItem(17.978611,-98.33389));
            mClusterManager.addItem(new MyItem(17.978611,-97.921111));
            mClusterManager.addItem(new MyItem(17.978611,-97.921118));
            mClusterManager.addItem(new MyItem(17.978611,-98.333891));
            mClusterManager.addItem(new MyItem(17.985506,-97.885519));
            mClusterManager.addItem(new MyItem(17.985506,-97.88552));
            mClusterManager.addItem(new MyItem(17.987778,-97.951657));
            mClusterManager.addItem(new MyItem(17.987778,-97.951647));
            mClusterManager.addItem(new MyItem(17.987778,-97.951637));
            mClusterManager.addItem(new MyItem(17.998872,-97.881408));
            mClusterManager.addItem(new MyItem(17.998872,-97.881409));
            mClusterManager.addItem(new MyItem(17.999722,-97.772768));
            mClusterManager.addItem(new MyItem(17.999722,-97.772758));
            mClusterManager.addItem(new MyItem(17.999722,-97.772748));
            mClusterManager.addItem(new MyItem(18.005001,-98.179712));
            mClusterManager.addItem(new MyItem(18.005001,-98.179702));
            mClusterManager.addItem(new MyItem(18.005001,-98.179692));
            mClusterManager.addItem(new MyItem(18.013611,-98.137212));
            mClusterManager.addItem(new MyItem(18.013611,-98.137202));
            mClusterManager.addItem(new MyItem(18.013611,-98.137192));
            mClusterManager.addItem(new MyItem(18.015556,-98.699167));
            mClusterManager.addItem(new MyItem(18.017321,-98.765834));
            mClusterManager.addItem(new MyItem(18.0175001,-98.70246));
            mClusterManager.addItem(new MyItem(18.017501,-98.70249));
            mClusterManager.addItem(new MyItem(18.017501,-98.702441));
            mClusterManager.addItem(new MyItem(18.018611,-98.541101));
            mClusterManager.addItem(new MyItem(18.018611,-98.541091));
            mClusterManager.addItem(new MyItem(18.018611,-98.541081));
            mClusterManager.addItem(new MyItem(18.073889,-98.248869));
            mClusterManager.addItem(new MyItem(18.075556,-98.133313));
            mClusterManager.addItem(new MyItem(18.075556,-98.133303));
            mClusterManager.addItem(new MyItem(18.075833,-97.996113));
            mClusterManager.addItem(new MyItem(18.075833,-97.996114));
            mClusterManager.addItem(new MyItem(18.076111,-97.911112));
            mClusterManager.addItem(new MyItem(18.076111,-97.911113));
            mClusterManager.addItem(new MyItem(18.079722,-98.14249));
            mClusterManager.addItem(new MyItem(18.079722,-98.14248));
            mClusterManager.addItem(new MyItem(18.079722,-98.14247));
            mClusterManager.addItem(new MyItem(18.079722,-98.14246));
            mClusterManager.addItem(new MyItem(18.081041,-97.917534));
            mClusterManager.addItem(new MyItem(18.083051,-97.913757));
            mClusterManager.addItem(new MyItem(18.083341,-97.924831));
            mClusterManager.addItem(new MyItem(18.083611,-98.326668));
            mClusterManager.addItem(new MyItem(18.083611,-98.326669));
            mClusterManager.addItem(new MyItem(18.084176,-97.923845));
            mClusterManager.addItem(new MyItem(18.085143,-97.920265));
            mClusterManager.addItem(new MyItem(18.088056,-97.873889));
            mClusterManager.addItem(new MyItem(18.088572,-97.915786));
            mClusterManager.addItem(new MyItem(18.088934,-97.918624));
            mClusterManager.addItem(new MyItem(18.089722,-97.904444));
            mClusterManager.addItem(new MyItem(18.091111,-98.101945));
            mClusterManager.addItem(new MyItem(18.091111,-98.101946));
            mClusterManager.addItem(new MyItem(18.091594,-98.037197));
            mClusterManager.addItem(new MyItem(18.095556,-98.087223));
            mClusterManager.addItem(new MyItem(18.095556,-98.087224));
            mClusterManager.addItem(new MyItem(18.099722,-98.296379));
            mClusterManager.addItem(new MyItem(18.099722,-98.296369));
            mClusterManager.addItem(new MyItem(18.099722,-98.296359));
            mClusterManager.addItem(new MyItem(18.105833,-98.070833));
            mClusterManager.addItem(new MyItem(18.106462,-98.312574));
            mClusterManager.addItem(new MyItem(18.106803,-98.312733));
            mClusterManager.addItem(new MyItem(18.107778,-98.056668));
            mClusterManager.addItem(new MyItem(18.107778,-98.056669));
            mClusterManager.addItem(new MyItem(18.109152,-98.312579));
            mClusterManager.addItem(new MyItem(18.109383,-97.900923));
            mClusterManager.addItem(new MyItem(18.110001,-98.484147));
            mClusterManager.addItem(new MyItem(18.110001,-98.484137));
            mClusterManager.addItem(new MyItem(18.110001,-98.484117));
            mClusterManager.addItem(new MyItem(18.112222,-98.062223));
            mClusterManager.addItem(new MyItem(18.112222,-98.062224));
            mClusterManager.addItem(new MyItem(18.113101,-98.313821));
            mClusterManager.addItem(new MyItem(18.114444,-98.241101));
            mClusterManager.addItem(new MyItem(18.114444,-98.241091));
            mClusterManager.addItem(new MyItem(18.114444,-98.241081));
            mClusterManager.addItem(new MyItem(18.115556,-97.552223));
            mClusterManager.addItem(new MyItem(18.115556,-97.552224));
            mClusterManager.addItem(new MyItem(18.116944,-98.077212));
            mClusterManager.addItem(new MyItem(18.116944,-98.077202));
            mClusterManager.addItem(new MyItem(18.116944,-98.077192));
            mClusterManager.addItem(new MyItem(18.118333,-98.401944));
            mClusterManager.addItem(new MyItem(18.11962 ,-97.542895));
            mClusterManager.addItem(new MyItem(18.121111,-98.008601));
            mClusterManager.addItem(new MyItem(18.121111,-98.008591));
            mClusterManager.addItem(new MyItem(18.121111,-98.008581));
            mClusterManager.addItem(new MyItem(18.121226,-98.084999));
            mClusterManager.addItem(new MyItem(18.122569,-98.083856));
            mClusterManager.addItem(new MyItem(18.122716,-98.143195));
            mClusterManager.addItem(new MyItem(18.122716,-98.143196));
            mClusterManager.addItem(new MyItem(18.124089,-98.086324));
            mClusterManager.addItem(new MyItem(18.124196,-98.086769));
            mClusterManager.addItem(new MyItem(18.124811,-98.082968));
            mClusterManager.addItem(new MyItem(18.125024,-98.083671));
            mClusterManager.addItem(new MyItem(18.125201,-98.083572));
            mClusterManager.addItem(new MyItem(18.126612,-98.325362));
            mClusterManager.addItem(new MyItem(18.1375 ,-98.082192));
            mClusterManager.addItem(new MyItem(18.137501,-98.082212));
            mClusterManager.addItem(new MyItem(18.137501,-98.082202));
            mClusterManager.addItem(new MyItem(18.137573,-98.079962));
            mClusterManager.addItem(new MyItem(18.140501,-97.554010));
            mClusterManager.addItem(new MyItem(18.143056,-98.071668));
            mClusterManager.addItem(new MyItem(18.143056,-98.071669));
            mClusterManager.addItem(new MyItem(18.145201,-97.504671));
            mClusterManager.addItem(new MyItem(18.146944,-98.122768));
            mClusterManager.addItem(new MyItem(18.146944,-98.122758));
            mClusterManager.addItem(new MyItem(18.146944,-98.122748));
            mClusterManager.addItem(new MyItem(18.147489,-98.400835));
            mClusterManager.addItem(new MyItem(18.148056,-98.474434));
            mClusterManager.addItem(new MyItem(18.148056,-98.474424));
            mClusterManager.addItem(new MyItem(18.244167,-96.956946));
            mClusterManager.addItem(new MyItem(18.245295,-97.493547));
            mClusterManager.addItem(new MyItem(18.247222,-97.034722));
            mClusterManager.addItem(new MyItem(18.247941,-96.867061));
            mClusterManager.addItem(new MyItem(18.248251,-98.064694));
            mClusterManager.addItem(new MyItem(18.248611,-98.271101));
            mClusterManager.addItem(new MyItem(18.248611,-98.271091));
            mClusterManager.addItem(new MyItem(18.248611,-98.271081));
            mClusterManager.addItem(new MyItem(18.248611,-98.316111));
            mClusterManager.addItem(new MyItem(18.251077,-97.057254));
            mClusterManager.addItem(new MyItem(18.253664,-98.456726));
            mClusterManager.addItem(new MyItem(18.254444,-98.016668));
            mClusterManager.addItem(new MyItem(18.254444,-98.016669));
            mClusterManager.addItem(new MyItem(18.255581,-96.983407));
            mClusterManager.addItem(new MyItem(18.256667,-98.102212));
            mClusterManager.addItem(new MyItem(18.256667,-98.102202));
            mClusterManager.addItem(new MyItem(18.256667,-98.102192));
            mClusterManager.addItem(new MyItem(18.25714 ,-96.864093));
            mClusterManager.addItem(new MyItem(18.257142,-98.487765));
            mClusterManager.addItem(new MyItem(18.257222,-98.012212));
            mClusterManager.addItem(new MyItem(18.257222,-98.012202));
            mClusterManager.addItem(new MyItem(18.257222,-98.012192));
            mClusterManager.addItem(new MyItem(18.259252,-96.853891));
            mClusterManager.addItem(new MyItem(18.259252,-96.853892));
            mClusterManager.addItem(new MyItem(18.259722,-98.060823));
            mClusterManager.addItem(new MyItem(18.259722,-97.323056));
            mClusterManager.addItem(new MyItem(18.259722,-98.060813));
            mClusterManager.addItem(new MyItem(18.259722,-98.060803));
            mClusterManager.addItem(new MyItem(18.262501,-98.983612));
            mClusterManager.addItem(new MyItem(18.262501,-98.983613));
            mClusterManager.addItem(new MyItem(18.262978,-97.139352));
            mClusterManager.addItem(new MyItem(18.264086,-98.428032));
            mClusterManager.addItem(new MyItem(18.264086,-98.428022));
            mClusterManager.addItem(new MyItem(18.264086,-98.428012));
            mClusterManager.addItem(new MyItem(18.264444,-97.146389));
            mClusterManager.addItem(new MyItem(18.265346,-97.143129));
            mClusterManager.addItem(new MyItem(18.265346,-97.14313));
            mClusterManager.addItem(new MyItem(18.265556,-98.295546));
            mClusterManager.addItem(new MyItem(18.265556,-98.197768));
            mClusterManager.addItem(new MyItem(18.265556,-98.295536));
            mClusterManager.addItem(new MyItem(18.265556,-98.197758));
            mClusterManager.addItem(new MyItem(18.265556,-98.197748));
            mClusterManager.addItem(new MyItem(18.265556,-98.295526));
            mClusterManager.addItem(new MyItem(18.266232,-97.151213));
            mClusterManager.addItem(new MyItem(18.267166,-97.153252));
            mClusterManager.addItem(new MyItem(18.267166,-97.153253));
            mClusterManager.addItem(new MyItem(18.267422,-98.61666));
            mClusterManager.addItem(new MyItem(18.267501,-98.219722));
            mClusterManager.addItem(new MyItem(18.268936,-97.160867));
            mClusterManager.addItem(new MyItem(18.268936,-97.160868));
            mClusterManager.addItem(new MyItem(18.269846,-97.148635));
            mClusterManager.addItem(new MyItem(18.271389,-98.761934));
            mClusterManager.addItem(new MyItem(18.271389,-98.761924));
            mClusterManager.addItem(new MyItem(18.271389,-98.761914));
            mClusterManager.addItem(new MyItem(18.271389,-98.761904));
            mClusterManager.addItem(new MyItem(18.271586,-98.589158));
            mClusterManager.addItem(new MyItem(18.272877,-97.161487));
            mClusterManager.addItem(new MyItem(18.276496,-96.859063));
            mClusterManager.addItem(new MyItem(18.276667,-97.067779));
            mClusterManager.addItem(new MyItem(18.276667,-97.06778));
            mClusterManager.addItem(new MyItem(18.278333,-98.966389));
            mClusterManager.addItem(new MyItem(18.278333,-98.029991));
            mClusterManager.addItem(new MyItem(18.278333,-97.262779));
            mClusterManager.addItem(new MyItem(18.278333,-97.262781));
            mClusterManager.addItem(new MyItem(18.278333,-98.02998));
            mClusterManager.addItem(new MyItem(18.278333,-98.02997));
            mClusterManager.addItem(new MyItem(18.278366,-98.525238));
            mClusterManager.addItem(new MyItem(18.278366,-98.525228));
            mClusterManager.addItem(new MyItem(18.278366,-98.525218));
            mClusterManager.addItem(new MyItem(18.280278,-97.544722));
            mClusterManager.addItem(new MyItem(18.280429,-98.062955));
            mClusterManager.addItem(new MyItem(18.281443,-98.05666));
            mClusterManager.addItem(new MyItem(18.281525,-97.095446));
            mClusterManager.addItem(new MyItem(18.281525,-97.095447));
            mClusterManager.addItem(new MyItem(18.281671,-98.056739));
            mClusterManager.addItem(new MyItem(18.282066,-98.059354));
            mClusterManager.addItem(new MyItem(18.282496,-98.063862));
            mClusterManager.addItem(new MyItem(18.284722,-96.992779));
            mClusterManager.addItem(new MyItem(18.284722,-96.992781));
            mClusterManager.addItem(new MyItem(18.285797,-98.969099));
            mClusterManager.addItem(new MyItem(18.286111,-97.326667));
            mClusterManager.addItem(new MyItem(18.286111,-96.868611));
            mClusterManager.addItem(new MyItem(18.286944,-96.834722));
            mClusterManager.addItem(new MyItem(18.287976,-98.616927));
            mClusterManager.addItem(new MyItem(18.287976,-98.616917));
            mClusterManager.addItem(new MyItem(18.287976,-98.616907));
            mClusterManager.addItem(new MyItem(18.288333,-97.288889));
            mClusterManager.addItem(new MyItem(18.288892,-97.283007));
            mClusterManager.addItem(new MyItem(18.288977,-97.283291));
            mClusterManager.addItem(new MyItem(18.290001,-98.948323));
            mClusterManager.addItem(new MyItem(18.290001,-98.948313));
            mClusterManager.addItem(new MyItem(18.290001,-98.948303));
            mClusterManager.addItem(new MyItem(18.293377,-97.164676));
            mClusterManager.addItem(new MyItem(18.293377,-97.164677));
            mClusterManager.addItem(new MyItem(18.293921,-97.171481));
            mClusterManager.addItem(new MyItem(18.294118,-98.599533));
            mClusterManager.addItem(new MyItem(18.294162,-98.73009));
            mClusterManager.addItem(new MyItem(18.294162,-98.73008));
            mClusterManager.addItem(new MyItem(18.294162,-98.73007));
            mClusterManager.addItem(new MyItem(18.294293,-97.168853));
            mClusterManager.addItem(new MyItem(18.294344,-98.603264));
            mClusterManager.addItem(new MyItem(18.294974,-98.603401));
            mClusterManager.addItem(new MyItem(18.295355,-98.599864));
            mClusterManager.addItem(new MyItem(18.295833,-97.810001));
            mClusterManager.addItem(new MyItem(18.296389,-97.002778));
            mClusterManager.addItem(new MyItem(18.296584,-97.114227));
            mClusterManager.addItem(new MyItem(18.296584,-97.114217));
            mClusterManager.addItem(new MyItem(18.296584,-97.114207));
            mClusterManager.addItem(new MyItem(18.296667,-97.776391));
            mClusterManager.addItem(new MyItem(18.296667,-97.776391));
            mClusterManager.addItem(new MyItem(18.296693,-97.157205));
            mClusterManager.addItem(new MyItem(18.298056,-97.384167));
            mClusterManager.addItem(new MyItem(18.298056,-98.603611));
            mClusterManager.addItem(new MyItem(18.298706,-97.033932));
            mClusterManager.addItem(new MyItem(18.298721,-98.602697));
            mClusterManager.addItem(new MyItem(18.298721,-98.602698));
            mClusterManager.addItem(new MyItem(18.299391,-98.603149));
            mClusterManager.addItem(new MyItem(18.299671,-98.193589));
            mClusterManager.addItem(new MyItem(18.299671,-98.193579));
            mClusterManager.addItem(new MyItem(18.299671,-98.193569));
            mClusterManager.addItem(new MyItem(18.299673,-97.031838));
            mClusterManager.addItem(new MyItem(18.301111,-98.227223));
            mClusterManager.addItem(new MyItem(18.301111,-98.227224));
            mClusterManager.addItem(new MyItem(18.302184,-98.608721));
            mClusterManager.addItem(new MyItem(18.302184,-98.608711));
            mClusterManager.addItem(new MyItem(18.302184,-98.608701));
            mClusterManager.addItem(new MyItem(18.303889,-97.088890));
            mClusterManager.addItem(new MyItem(18.303889,-97.088891));
            mClusterManager.addItem(new MyItem(18.304518,-98.592751));
            mClusterManager.addItem(new MyItem(18.305833,-98.321101));
            mClusterManager.addItem(new MyItem(18.305833,-98.321091));
            mClusterManager.addItem(new MyItem(18.305833,-98.321081));
            mClusterManager.addItem(new MyItem(18.307112,-98.601141));
            mClusterManager.addItem(new MyItem(18.307135,-98.60226));
            mClusterManager.addItem(new MyItem(18.312971,-97.959132));
            mClusterManager.addItem(new MyItem(18.312971,-97.959133));
            mClusterManager.addItem(new MyItem(18.314444,-96.835834));
            mClusterManager.addItem(new MyItem(18.314444,-96.835835));
            mClusterManager.addItem(new MyItem(18.315094,-97.114724));
            mClusterManager.addItem(new MyItem(18.316111,-97.615001));
            mClusterManager.addItem(new MyItem(18.317689,-98.572489));
            mClusterManager.addItem(new MyItem(18.318629,-97.349241));
            mClusterManager.addItem(new MyItem(18.319167,-98.193601));
            mClusterManager.addItem(new MyItem(18.319167,-98.193591));
            mClusterManager.addItem(new MyItem(18.319167,-98.193581));
            mClusterManager.addItem(new MyItem(18.319192,-98.84056));
            mClusterManager.addItem(new MyItem(18.319645,-98.590701));
            mClusterManager.addItem(new MyItem(18.320033,-98.845547));
            mClusterManager.addItem(new MyItem(18.320033,-98.845548));
            mClusterManager.addItem(new MyItem(18.320035,-98.849307));
            mClusterManager.addItem(new MyItem(18.320398,-98.844351));
            mClusterManager.addItem(new MyItem(18.320445,-98.844499));
            mClusterManager.addItem(new MyItem(18.321401,-97.343599));
            mClusterManager.addItem(new MyItem(18.323056,-98.845266));
            mClusterManager.addItem(new MyItem(18.323889,-97.096667));
            mClusterManager.addItem(new MyItem(18.324401,-98.646221));
            mClusterManager.addItem(new MyItem(18.324487,-97.351565));
            mClusterManager.addItem(new MyItem(18.325278,-97.079167));
            mClusterManager.addItem(new MyItem(18.326454,-98.84559));
            mClusterManager.addItem(new MyItem(18.326854,-98.257205));
            mClusterManager.addItem(new MyItem(18.326854,-98.257206));
            mClusterManager.addItem(new MyItem(18.328415,-97.341945));
            mClusterManager.addItem(new MyItem(18.329035,-98.276568));

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.0337592,-98.1869488), 1));
        }
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
        markerOptions.snippet("@DavidHackro");
        markerOptions.title("Sigueme en Twitter");
        super.onBeforeClusterItemRendered(item, markerOptions);
    }
}
}
