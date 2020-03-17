package com.example.petrolstation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petrolstation.models.GasStation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SuggestionActivity extends AppCompatActivity {


    EditText stationName, stationLocation, stationLat, stationLong;
    Button submitBtn;

    private String name, location, latitude, longitude;

    private static final String TAG = "SuggestionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        stationName = findViewById(R.id.station_name);
        stationLocation = findViewById(R.id.station_location);
        stationLat = findViewById(R.id.station_latitude);
        stationLong = findViewById(R.id.station_longitude);

        submitBtn = findViewById(R.id.submit_button);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ProgressDialog progress;
        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait");
        progress.setMessage("Adding to database...");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GasStation gasStation = getFormData();
                // Create a new user with a first and last name
                Map<String, Object> station = new HashMap<>();
                station.put("name", gasStation.getName());
                station.put("location", gasStation.getLocation());
                station.put("latitude", gasStation.getLatitude());
                station.put("longitude", gasStation.getLongitude());
                station.put("petrolPrice", gasStation.getPetrolPrice());
                station.put("dieselPrice", gasStation.getDieselPrice());

                progress.show();

// Add a new document with a generated ID
                db.collection("suggested_station")
                        .add(station)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(SuggestionActivity.this,
                                        "DocumentSnapshot added with ID: " +
                                                documentReference.getId(), Toast.LENGTH_SHORT).show();
                                progress.dismiss();

                                AlertDialog.Builder builder = new AlertDialog.Builder(SuggestionActivity.this);
                                builder
                                        .setTitle("Note")
                                        .setMessage("Thank you for your Suggestion, This will be" +
                                                "reviewed by admin and will be added soon")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do things
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(SuggestionActivity.this,
                                        "Error adding document", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    public GasStation getFormData() {
        name = stationName.getText().toString();
        location = stationLocation.getText().toString();
        latitude = stationLat.getText().toString();
        longitude = stationLong.getText().toString();

        GasStation gasStation = new GasStation(name, location, latitude, longitude, null, null);

        return gasStation;
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(SuggestionActivity.this, MainActivity.class));
    }
}
