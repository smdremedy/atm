package com.adaptris.atmlocator;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddATMActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener {

    private static final int REQUEST_PLACE_PICKER = 123;
    private static final String LOG_TAG = AddATMActivity.class.getSimpleName();
    private static final int RESOLUTION_REQUEST_CODE = 321;
    @Bind(R.id.pickAddressButton)
    Button pickAddressButton;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atm);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.addAll("ING BANK", "Milenium", "PKO BP", "Alior", "Krzak", "mBank", "ING Nederlanden");

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();
        locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1000);

    }

    @OnClick(R.id.pickAddressButton)
    public void pickAddress() {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            // Start the Intent by requesting a result, identified by a request code.
            startActivityForResult(intent, REQUEST_PLACE_PICKER);


            // Hide the pick option in the UI to prevent users from starting the picker
            // multiple times.
            // showPickAction(false);


        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), this, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {


        if(connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RESOLUTION_REQUEST_CODE);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public void onConnected(Bundle bundle) {


        Log.d(LOG_TAG, "Connected");
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(lastLocation != null) {
            handleLocation(lastLocation);
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }


    }


    private void handleLocation(Location lastLocation) {
        Log.d(LOG_TAG, "Last location:" + lastLocation);
    }


    @Override
    public void onConnectionSuspended(int i) {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);


    }


    @Override
    public void onLocationChanged(Location location) {
        handleLocation(location);
    }
}
