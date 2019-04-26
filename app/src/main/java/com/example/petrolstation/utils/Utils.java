package com.example.petrolstation.utils;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.petrolstation.R;
import com.example.petrolstation.models.FuelPrice;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public  class Utils {

    public static void moveCamera(GoogleMap mMap, LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void FuelDetailDialog(Context context, FuelPrice fuelPrice) {
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
}
