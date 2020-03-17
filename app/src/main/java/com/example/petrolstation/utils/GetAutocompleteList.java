//package com.example.petrolstation.utils;
//
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.common.data.DataBufferUtils;
//import com.google.android.gms.location.places.AutocompletePrediction;
//import com.google.android.gms.location.places.AutocompletePredictionBuffer;
//import com.google.android.gms.location.places.Places;
//
//import java.util.ArrayList;
//import java.util.concurrent.TimeUnit;
//
//public class GetAutocompleteList {
//
//    private ArrayList<AutocompletePrediction> getAutocomplete(CharSequence constraint) {
//        if (mGoogleApiClient.isConnected()) {
//            Log.i(TAG, "Starting autocomplete query for: " + constraint);
//
//            // Submit the query to the autocomplete API and retrieve a PendingResult that will
//            // contain the results when the query completes.
//            PendingResult<AutocompletePredictionBuffer> results =
//                    Places.GeoDataApi
//                            .getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
//                                    mBounds, mPlaceFilter);
//
//            // This method should have been called off the main UI thread. Block and wait for at most 60s
//            // for a result from the API.
//            AutocompletePredictionBuffer autocompletePredictions = results
//                    .await(60, TimeUnit.SECONDS);
//
//            // Confirm that the query completed successfully, otherwise return null
//            final Status status = autocompletePredictions.getStatus();
//            if (!status.isSuccess()) {
//                Toast.makeText(getContext(), "Error contacting API: " + status.toString(),
//                        Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "Error getting autocomplete prediction API call: " + status.toString());
//                autocompletePredictions.release();
//                return null;
//            }
//
//            Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
//                    + " predictions.");
//
//            // Freeze the results immutable representation that can be stored safely.
//            return DataBufferUtils.freezeAndClose(autocompletePredictions);
//        }
//        Log.e(TAG, "Google API client is not connected for autocomplete query.");
//        return null;
//    }
//
//}
