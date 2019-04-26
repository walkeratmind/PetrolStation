package com.example.petrolstation;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.petrolstation.adapter.FuelPriceViewAdapter;
import com.example.petrolstation.listener.RecyclerTouchListener;
import com.example.petrolstation.models.FuelPrice;
import com.example.petrolstation.utils.Utils;
import com.example.petrolstation.web.scarping.GetFuelPrices;
import com.example.petrolstation.web.scarping.parser.ParserResponseInterface;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;


public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    // URL of a website...
    String url = "http://nepaloil.com.np/retailprice";
    // Connecting to website
    Document content;
    String body = "nothing";

    private RecyclerView recyclerView;
//    ArrayList<FuelPrice> fuelPriceList;

//    FuelPrice fuelPrice;

    TextView textView;
//    Thread getPrice = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                content = (Document) Jsoup.connect(url).get();
//
//                // Use Elements to get the meta-data
//                Element element = (Element) content.getElementsByTagName("table");
//                body = String.valueOf(content);
//                Log.d(TAG, "on Thread Run: Content" + content);
//                textView.setText(body);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.d(TAG, "On Thread Error: " + e);
//            }
//        }
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textView = findViewById(R.id.body_content);
        recyclerView = findViewById(R.id.recycler_view);

        // start the thread
//        getPrice.start();

//        fuelPriceList = null;

        showPriceDetails();


    }

    @Override
    protected void onResume() {
//        showPriceDetails();
        super.onResume();
    }

    public void showPriceDetails() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            // RUN WEB SCARPING HERE >>>
            new GetFuelPrices(new ParserResponseInterface() {
                @Override
                public void onParsingDone(ArrayList<FuelPrice> fuelPriceArrayList) {
                    if (fuelPriceArrayList != null) {
                        textView.setText("Petrol Price: " + fuelPriceArrayList.get(0).getPetrolPrice());

                        //show values in recycler view if background task is finished
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());

                        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                                LinearLayoutManager.VERTICAL));

                        FuelPriceViewAdapter fuelPriceViewAdapter = new FuelPriceViewAdapter(fuelPriceArrayList);
                        recyclerView.setAdapter(fuelPriceViewAdapter);

                        recyclerView.addOnItemTouchListener(
                                new RecyclerTouchListener(getApplicationContext(), recyclerView,
                                        new RecyclerTouchListener.ClickListener() {
                                            @Override
                                            public void onClick(View view, int position) {
                                                Utils.FuelDetailDialog(getApplicationContext(),
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
            }).execute(url);

        } else {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.coordinator_layout), "No internet connection!", Snackbar.LENGTH_LONG)
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

