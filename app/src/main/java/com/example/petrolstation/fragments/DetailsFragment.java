package com.example.petrolstation.fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.petrolstation.R;
import com.example.petrolstation.adapter.FuelPriceViewAdapter;
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
    String url = "http://nepaloil.com.np/retailprice";
    Document content;
    String body = "nothing";

    // For transfering data to async task
    Object dataTransfer[] = new Object[2];  //for transfering url and context

    private RecyclerView recyclerView;

    TextView textView;
    ProgressBar progressBar;
    LinearLayout progressLayout;

    // Create Empty Constructor
    public DetailsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_details, container, false);

        textView = view.findViewById(R.id.body_content);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar  = view.findViewById(R.id.progress_circular);

        progressLayout = view.findViewById(R.id.progress_bar);


        dataTransfer[0] = getContext();
        dataTransfer[1] = url;

        showPriceDetails();
        return view;
    }


    public void showPriceDetails() {
        if (Utils.isNetworkAvailable(getContext())) {

//            progressLayout.setVisibility(View.VISIBLE);
            // RUN WEB SCARPING HERE >>>
            new GetFuelPrices(new ParserResponseInterface() {
                @Override
                public void onParsingDone(ArrayList<FuelPrice> fuelPriceArrayList) {
//                    progressLayout.setVisibility(View.GONE);
                    if (fuelPriceArrayList != null) {
//                        textView.setText("Petrol Price: " + fuelPriceArrayList.get(0).getPetrolPrice());


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
                                                Utils.FuelDetailDialog(getContext(),
                                                        fuelPriceArrayList.get(position));
                                            }

                                            @Override
                                            public void onLongClick(View view, int position) {

                                            }
                                        })
                        );

//                        fuelPriceList = fuelPriceArrayList;
                    } else {
                        textView.setText("Error while Web Scarping");
                    }
                }
            }).execute(dataTransfer);

        } else {
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
    }


}
