package com.example.petrolstation.utils;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.petrolstation.R;
import com.example.petrolstation.models.FuelPrice;
import com.example.petrolstation.models.GasStation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public  class Utils {

    private static final String TAG = "Utils";

    public static void moveCamera(GoogleMap mMap, LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showFuelDetailDialog(Context context, FuelPrice fuelPrice) {
        Dialog fuelPriceDialog = new Dialog(context);
        fuelPriceDialog.setContentView(R.layout.fuel_price_detail);
        fuelPriceDialog.setTitle("Price Details");
        fuelPriceDialog.setCancelable(true);

        // set custom dialog components
        TextView effectiveDate, petrolPrice, dieselPrice, kerosenePrice, lpgPrice;
        effectiveDate = fuelPriceDialog.findViewById(R.id.effective_date);
        petrolPrice = fuelPriceDialog.findViewById(R.id.petrol_price);
        dieselPrice = fuelPriceDialog.findViewById(R.id.diesel_price);
        kerosenePrice = fuelPriceDialog.findViewById(R.id.kerosene_price);
        lpgPrice = fuelPriceDialog.findViewById(R.id.lpg_price);

        effectiveDate.setText(fuelPrice.getEffectiveDate());
        petrolPrice.setText(fuelPrice.getPetrolPrice());
        dieselPrice.setText(fuelPrice.getDieselPrice());
        kerosenePrice.setText(fuelPrice.getKerosenePrice());
        lpgPrice.setText(fuelPrice.getLpgPrice());

        fuelPriceDialog.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuelPriceDialog.dismiss();
            }
        });

        fuelPriceDialog.show();
    }

    public static ArrayList<GasStation> getFirestoreStatonList() {

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        ArrayList<GasStation> gasStations = new ArrayList<>();
        db.collection("stations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                GasStation station = document.toObject(GasStation.class);

                                Log.d(TAG, "station: " + station.getName());
                                gasStations.add(station);

                            }
                            Log.d(TAG, "Gas station: " + gasStations.size());
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return gasStations;
    }

}
