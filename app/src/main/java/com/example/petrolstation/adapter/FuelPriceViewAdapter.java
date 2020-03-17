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
import com.example.petrolstation.utils.Utils;

import java.util.ArrayList;

public class FuelPriceViewAdapter extends RecyclerView.Adapter<FuelPriceViewAdapter.MyVeiwHolder> {

    private Context context;
    private ArrayList<FuelPrice> fuelPriceList;

    public FuelPriceViewAdapter(ArrayList<FuelPrice> fuelPriceList) {
        this.fuelPriceList = fuelPriceList;
    }

    @NonNull
    @Override
    public MyVeiwHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fuel_price_list_item, viewGroup, false);
        context = viewGroup.getContext();
        return new MyVeiwHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVeiwHolder myVeiwHolder, int position) {
            FuelPrice fuelPrice = fuelPriceList.get(position);

        myVeiwHolder.effectiveDate.setText(fuelPrice.getEffectiveDate());
        myVeiwHolder.petrolPrice.setText(fuelPrice.getPetrolPrice());

        myVeiwHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showFuelDetailDialog(context, fuelPriceList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return fuelPriceList.size();
    }

    public class MyVeiwHolder extends RecyclerView.ViewHolder {

        public TextView effectiveDate, petrolPrice;

        public MyVeiwHolder(@NonNull View itemView) {
            super(itemView);
            effectiveDate = itemView.findViewById(R.id.effective_date);
            petrolPrice = itemView.findViewById(R.id.petrol_price);

        }
    }
}
