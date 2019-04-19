package com.example.petrolstation;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import static com.example.petrolstation.Constants.ERROR_DIALOG_REQUEST;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isServicesOk()) {
            init();
        }
    }

    private void init() {

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    private boolean isServicesOk() {
        Log.d(TAG, "isServicesOK: Checking google Services Version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS) {
            // Everything is cool and user can request for map
            Log.d(TAG, "isServicesOK: Google Play Services is working ");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
//            an error is occured but can be fixed
            Log.d(TAG, "isServicesOk: an error occured but it can be fixed");

            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();

        } else {
            Toast.makeText(this, "can't request for map", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
