package com.example.bookmark;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.audiofx.Virtualizer;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserAddressActivity extends FragmentActivity implements OnMapReadyCallback {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Geocoder geocoder;

    int PERMISSION_ID = 44;

    private TextView mUserAddressTextView;
    private Button mUserAddressPositiveButton;
    private Button mUserAddressNegativeButton;
    private double mLatitude;
    private double mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);

        mAuth = FirebaseAuth.getInstance();

        mUserAddressTextView = findViewById(R.id.new_user_address);
        mUserAddressNegativeButton = findViewById(R.id.new_user_isAddress_buttonNegative);
        mUserAddressPositiveButton = findViewById(R.id.new_user_isAddress_buttonPositive);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        mUserAddressNegativeButton.setOnClickListener(v -> toMainActivity());
        mUserAddressPositiveButton.setOnClickListener(v -> saveAddress());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng address = new LatLng(mLatitude, mLongitude);
        mMap.addMarker(new MarkerOptions().position(address).title("Meu endereço"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();

                    if (location == null) {
                        requestNewLocation();
                    } else {
                        mLatitude = location.getLatitude();
                        mLongitude = location.getLongitude();

                        geocoder = new Geocoder(this, Locale.getDefault());

                        try {
                            List<Address> addresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
                            Log.e("UserAddressActivity", "getLastLocation: " + addresses.get(0).getAddressLine(0));
                            mUserAddressTextView.setText(addresses.get(0).getAddressLine(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Snackbar gpsInativeError = Snackbar.make(getCurrentFocus(), "Por favor, ligue o GPS do dispositivo e tente novamente...", Snackbar.LENGTH_LONG);
                gpsInativeError.show();

                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }

    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void requestNewLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(5);
            mLocationRequest.setFastestInterval(0);
            mLocationRequest.setNumUpdates(1);

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();

            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

            try {
                List<Address> addresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
                Log.e("UserAddressActivity", "onLocationResult: " + addresses.get(0).getAddressLine(0));
                mUserAddressTextView.setText(addresses.get(0).getAddressLine(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, PERMISSION_ID);
    }

    private void saveAddress() {
        DocumentReference firestoreUser = database.collection("users").document(mAuth.getUid());

        firestoreUser.update("latitude", mLatitude)
                .addOnSuccessListener(aVoid -> {
                    firestoreUser.update("longitude", mLongitude)
                            .addOnSuccessListener(aVoid2 -> {
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Snackbar setNameError = Snackbar.make(getCurrentFocus(), "Não foi possível atualizar a longitude, tente novamente mais tarde...", Snackbar.LENGTH_LONG);
                                setNameError.show();
                            });
                })
                .addOnFailureListener(e -> {
                    Snackbar setNameError = Snackbar.make(getCurrentFocus(), "Não foi possível atualizar a latitude, tente novamente mais tarde...", Snackbar.LENGTH_LONG);
                    setNameError.show();
                });
    }

    private void toMainActivity() {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
}