package com.example.petrolstation.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petrolstation.R;
import com.example.petrolstation.adapter.StationListViewAdapter;
import com.example.petrolstation.models.GasStation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class GasStationListFragment extends Fragment {

    private static final String TAG = "GasStationListFragment";
    RecyclerView recyclerView;

    FirebaseFirestore db;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_details, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        db = FirebaseFirestore.getInstance();

        ArrayList<GasStation> list = new ArrayList<>();

//        showRecyclerView(getFirestoreStatonList());
        showFirestoreData();
        return view;
    }

    private void showFirestoreData() {

        ArrayList<GasStation> gasStationArrayList = new ArrayList<GasStation>();

        progressDialog = ProgressDialog.show(getContext(), "Please wait.",
                "Fetching from Database.", true);
        db.collection("stations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                GasStation station = document.toObject(GasStation.class);

                                GasStation object = station;
                                Log.d(TAG, "Station: " + object.getName());
                                gasStationArrayList.add(object);

                            }
                            Log.d(TAG, "ArrayList: " + gasStationArrayList.size());
                            showRecyclerView(gasStationArrayList);

                            progressDialog.dismiss();

                        } else {
                            progressDialog.dismiss();
                            Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(getContext(), "Error getting data", Toast.LENGTH_SHORT).show();
                            Snackbar snackbar = Snackbar
                                    .make(getView(), "Check Your Internet Connection!", Snackbar.LENGTH_LONG)
                                    .setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showFirestoreData();
                                        }
                                    });

                            // Changing message text color
                            snackbar.setActionTextColor(Color.RED);

                            // Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.YELLOW);
                            snackbar.show();
                        }
                    }
                });

    }

//    public void showRecyclerView() {
//        db = FirebaseFirestore.getInstance();
//
//        Query query = db.collection("stations")
//                .orderBy("productName", Query.Direction.ASCENDING);
//
////        FirebaseRecyclerOptions<GasStation> options =
////                new FirebaseRecyclerOptions.Builder<GasStation>()
////                        .setQuery(query, GasStation.class)
////                        .build();
//
//
//    }
//    private static class ProductViewHolder extends RecyclerView.ViewHolder {
//        private View view;
//
//        ProductViewHolder(View itemView) {
//            super(itemView);
//            view = itemView;
//        }
//
//        void setProductName(String productName) {
//            TextView textView = view.findViewById(R.id.text_view);
//            textView.setText(productName);
//        }
//    }

    public void showRecyclerView(ArrayList<GasStation> gasStationArrayList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));

        StationListViewAdapter stationListViewAdapter = new StationListViewAdapter(gasStationArrayList);
        recyclerView.setAdapter(stationListViewAdapter);
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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                GasStation station = document.toObject(GasStation.class);

                                GasStation object = station;
                                Log.d(TAG, "Station: " + object.getName());
                                gasStations.add(object);

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
