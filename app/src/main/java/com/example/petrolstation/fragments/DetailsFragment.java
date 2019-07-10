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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petrolstation.R;
import com.example.petrolstation.adapter.FuelPriceViewAdapter;
import com.example.petrolstation.database.DatabaseHelper;
import com.example.petrolstation.listener.RecyclerTouchListener;
import com.example.petrolstation.models.FuelPrice;
import com.example.petrolstation.utils.Utils;
import com.example.petrolstation.web.scarping.GetFuelPrices;
import com.example.petrolstation.web.scarping.parser.ParserResponseInterface;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class DetailsFragment extends Fragment {

    private static final String TAG = "DetailsFragment";

    // URL of a website...
    String url = "http://noc.org.np/retailprice";
    Document content;
    String body = "nothing";

    // For transfering data to async task
    Object dataTransfer[] = new Object[2];  //for transfering url and context

    private RecyclerView recyclerView;

    TextView textView;
    ProgressDialog progressDialog;
    // for retrieving sqlite database
    private DatabaseHelper databaseHelper;

    ArrayList<FuelPrice> fuelPriceList;

    Thread scarpingBackgroundThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "onThread: " + "Running Scarping Task in Background Thread");
            runScarpingInBackground();


        }
    });

    // Create Empty Constructor
    public DetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_list:
                if (Utils.isNetworkAvailable(getContext())) {
                    databaseHelper.cleanTable();
                    runScarpingTask();
                } else {
                    showNetworkErrorBar();
                }
        }
        return true;
    }

    private void showNetworkErrorBar() {
        Snackbar snackbar = Snackbar
                .make(getView().findViewById(R.id.coordinator_layout), "No internet connection!", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPriceDetails();
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

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(getView()
                .findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_details, container, false);

        textView = view.findViewById(R.id.body_content);
        recyclerView = view.findViewById(R.id.recycler_view);

        dataTransfer[0] = getContext();
        dataTransfer[1] = url;

        databaseHelper = new DatabaseHelper(getContext());
        fuelPriceList = new ArrayList<>();

        showPriceDetails();
        return view;
    }


    public void showPriceDetails() {
        Log.d(TAG, "SQLITE Total FuelPriceList Items : " + databaseHelper.getFuelPriceCount());

        if (!databaseHelper.isFuelPriceDetailEmpty()) {
            Log.d(TAG, "From SQLITE, table is not empty");

            Toast.makeText(getContext(), "Getting from Sqlite", Toast.LENGTH_SHORT).show();
            fuelPriceList = databaseHelper.getAllFuelPriceDetail();
            showRecyclerView(fuelPriceList);

//            if (Utils.isNetworkAvailable(getContext())) {
//                scarpingBackgroundThread.start();
//            }
        } else {
            if (Utils.isNetworkAvailable(getContext())) {
                Log.d(TAG, "Sqlite:  fuelPrice tableis empty");
                runScarpingTask();
            } else {
                showNetworkErrorBar();
            }
        }
    }

    private void showRecyclerView(ArrayList<FuelPrice> fuelPriceArrayList) {
        //show values in recycler view if background task is finished
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));

        FuelPriceViewAdapter fuelPriceViewAdapter = new FuelPriceViewAdapter(fuelPriceArrayList);
        recyclerView.setAdapter(fuelPriceViewAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getContext(), recyclerView,
                        new RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Utils.showFuelDetailDialog(getContext(),
                                        fuelPriceArrayList.get(position));
                            }

                            @Override
                            public void onLongClick(View view, int position) {

                            }
                        })
        );
    }

    public void runScarpingTask() {
        // RUN WEB SCARPING HERE >>>
        progressDialog = ProgressDialog.show(getContext(), "Please wait.",
                "Fetching route information.", true);
        new GetFuelPrices(new ParserResponseInterface() {
            @Override
            public void onParsingDone(ArrayList<FuelPrice> fuelPriceArrayList) {
                // remove the progress bar
                progressDialog.dismiss();

                if (fuelPriceArrayList != null) {
                    showSnackBar("Updated from Web Scarping");
//                        textView.setText("Petrol Price: " + fuelPriceArrayList.get(0).getPetrolPrice());
                    // store value in arraylist list
                    //show values in recycler view if background task is finished
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                            LinearLayoutManager.VERTICAL));

                    FuelPriceViewAdapter fuelPriceViewAdapter = new FuelPriceViewAdapter(fuelPriceArrayList);
                    recyclerView.setAdapter(fuelPriceViewAdapter);

                    recyclerView.addOnItemTouchListener(
                            new RecyclerTouchListener(getContext(), recyclerView,
                                    new RecyclerTouchListener.ClickListener() {
                                        @Override
                                        public void onClick(View view, int position) {
                                            Utils.showFuelDetailDialog(getContext(),
                                                    fuelPriceArrayList.get(position));
                                        }

                                        @Override
                                        public void onLongClick(View view, int position) {

                                        }
                                    })
                    );
//                        fuelPriceList = fuelPriceArrayList;
                } else {
                    Snackbar snackbar = Snackbar
                            .make(getView().findViewById(R.id.coordinator_layout),
                                    "Failed to perform Web Scarping!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    databaseHelper.cleanTable();
                                    runScarpingTask();
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
        }).execute(dataTransfer);
    }

    public void runScarpingInBackground() {
        // RUN WEB SCARPING ON BACKGROUND HERE >>>

        // Run this if data is in sqlite but
        // if want to update the sqlite data after cleaning sqlite

        databaseHelper.cleanTable();
        new GetFuelPrices(new ParserResponseInterface() {
            @Override
            public void onParsingDone(ArrayList<FuelPrice> fuelPriceArrayList) {
                // remove the progress bar
                if (fuelPriceArrayList != null) {
//                        textView.setText("Petrol Price: " + fuelPriceArrayList.get(0).getPetrolPrice());
                    // store value in arraylist list
                    //show values in recycler view if background task is finished

//                        fuelPriceList = fuelPriceArrayList;
                } else {
                }
            }
        }).execute(dataTransfer);
    }

}
