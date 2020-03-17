package com.example.petrolstation.web.scarping;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.petrolstation.database.DatabaseHelper;
import com.example.petrolstation.models.FuelPrice;
import com.example.petrolstation.web.scarping.parser.ParserResponseInterface;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetFuelPrices extends AsyncTask<Object, Void, ArrayList<FuelPrice>> {

    private static final String TAG = "Web Scarping";
    private ParserResponseInterface parserResponseInterface;

    // for storing price details
    ArrayList<FuelPrice> fuelPriceArrayList = new ArrayList<>();

    DatabaseHelper databaseHelper;
    Context context;

    public GetFuelPrices(ParserResponseInterface parserResponseInterface) {
        this.parserResponseInterface = parserResponseInterface;
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected ArrayList<FuelPrice> doInBackground(Object... objects) {

        context = (Context) objects[0];
        // URL of a website...
        String url = (String) objects[1];

        databaseHelper = new DatabaseHelper(context);

        Log.d(TAG, "is Table exist: " + databaseHelper.isTableExists(FuelPrice.TABLE_NAME, true));

        if (!databaseHelper.isFuelPriceDetailEmpty()) {
            // Already in sqlite Database
            fuelPriceArrayList = databaseHelper.getAllFuelPriceDetail();
            Log.d(TAG, "Already in Sqlite");
            isCancelled();
        }
        // Connecting to website
        Document content;

        Elements tabelContent;

        Elements elementData;


        try {
            content = Jsoup.connect(url).timeout(0).get();  //set timeout

            Log.d(TAG, "Jsoup Content: " + content);


            // Use Elements to get the meta-data
            tabelContent = content.select("#DataTables_Table_0");
            elementData = content.select("tbody");

            Elements rowList = elementData.select("tr");
            Log.d(TAG, "Total Rows : " + rowList.text());


            for (Element rowData : rowList) {
                Log.d(TAG, "Row Data: " + rowData.text());

                Elements row = rowData.select("td");

                FuelPrice fuelPrice = null;

                List<String> list = new ArrayList<>();

                int i = 0;
                for (Element data : row) {
                    i++;

                    String value = data.text();
                    Log.d(TAG, "Value: " + value);
                    list.add(value);

                }
                if (list.size() >= 8) {
                    fuelPrice = new FuelPrice(i, list.get(0), list.get(1), list.get(2), list.get(3),
                            list.get(4), list.get(5), list.get(6), list.get(7));

                    // add to SQLITE database
                    long id = databaseHelper.insertFuelDetail(fuelPrice);
                    Log.d(TAG, "Petrol Price: " + fuelPrice.getPetrolPrice());
//                    Log.d(TAG, "SQLITE Data: " + databaseHelper.getFuelPriceDetail(id).getDieselPrice());
                    fuelPriceArrayList.add(fuelPrice);
                } else {
                    fuelPrice = null;
                    Log.d(TAG, "Failed while Initializing Object...");
                }


                // Add the object to Array List
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error on Scarping: " + e);
            fuelPriceArrayList = null;

        }

        return fuelPriceArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<FuelPrice> fuelPriceArrayList) {
        super.onPostExecute(fuelPriceArrayList);

        Log.d(TAG, "is Table exist: " + databaseHelper.isTableExists(FuelPrice.TABLE_NAME, true));

        parserResponseInterface.onParsingDone(fuelPriceArrayList);
    }
}
