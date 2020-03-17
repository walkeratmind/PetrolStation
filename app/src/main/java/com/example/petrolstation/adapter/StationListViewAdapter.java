package com.example.petrolstation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.petrolstation.R;
import com.example.petrolstation.models.FuelPrice;
import com.example.petrolstation.models.GasStation;

import java.util.ArrayList;


public class StationListViewAdapter extends RecyclerView.Adapter<StationListViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GasStation> gasStationArrayList;

    public StationListViewAdapter(ArrayList<GasStation> gasStationArrayList) {
        this.gasStationArrayList = gasStationArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.station_list_item, viewGroup, false);
        context = viewGroup.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        GasStation gasStation = gasStationArrayList.get(i);

        myViewHolder.stationName.setText(gasStation.getName());
        myViewHolder.stationLocation.setText(gasStation.getLocation());
        myViewHolder.latLng.setText(gasStation.getLatitude() + ", " + gasStation.getLongitude());
        myViewHolder.petrolPrice.setText(gasStation.getPetrolPrice());
        myViewHolder.dieselPrice.setText(gasStation.getDieselPrice());
    }

    @Override
    public int getItemCount() {
        return gasStationArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView stationName, stationLocation, latLng, petrolPrice, dieselPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            stationName = itemView.findViewById(R.id.station_name);
            stationLocation = itemView.findViewById(R.id.station_location);
            latLng = itemView.findViewById(R.id.station_latlng);
            petrolPrice = itemView.findViewById(R.id.petrol_price);
            dieselPrice= itemView.findViewById(R.id.diesel_price);
        }
    }
}
