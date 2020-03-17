package com.example.petrolstation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.arsy.maps_library.MapRadar;
import com.arsy.maps_library.MapRipple;
import com.example.petrolstation.models.GasStation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.example.petrolstation.Constants.DEFAULT_ZOOM;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    private static final String TAG = "nearbyplacesdata";

    private String googlePlacesData;
    private GoogleMap mMap;
    String url;
    Context context;

    // for animation
    LatLng latLng;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Object... objects) {

        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        context = (Context) objects[2];
        double[] location = (double[]) objects[3];

        // for ripple animation
        latLng = new LatLng(location[0], location[1]);
//        Log.d(TAG,"latlng: " + latLng);


        DownloadURL downloadURL = new DownloadURL();

        try {
            googlePlacesData = downloadURL.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {

        List<HashMap<String, String>> nearbyPlaceList = null;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        Log.d(TAG, "called parse method");
//        showNearbyPlaces(nearbyPlaceList);
        if (nearbyPlaceList != null) {
            showNearbyPlaces(nearbyPlaceList);
        }
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList) {
        for (int i = 0; i < nearbyPlaceList.size(); i++) {
            Log.d(TAG, " Nearby Places: " + nearbyPlaceList.get(i));
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(Objects.requireNonNull(googlePlace.get("lat")));
            double lng = Double.parseDouble(Objects.requireNonNull(googlePlace.get("lng")));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            markerOptions.icon(bitmapDescriptorFromVector(context, R.drawable.ic_local_gas_station_purple_24dp));
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM));

//            Utils.moveCamera(mMap, latLng, DEFAULT_ZOOM);
        }
    }

    public void showFirebaseStations() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        MarkerOptions markerOptions = new MarkerOptions();

        db.collection("stations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                GasStation station = document.toObject(GasStation.class);

                                Log.d(TAG, "station: " + station.getName());

                                LatLng latLng = new LatLng(Double.parseDouble(station.getLatitude()),
                                        Double.parseDouble(station.getLongitude()));
                                markerOptions.position(latLng);
                                markerOptions.title(station.getName() + " : " + station.getLocation());
                                markerOptions.icon(bitmapDescriptorFromVector(context, R.drawable.ic_local_gas_station_purple_24dp));
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                                mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM));

//            Utils.moveCamera(mMap, latLng, DEFAULT_ZOOM);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_local_gas_station_purple_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}