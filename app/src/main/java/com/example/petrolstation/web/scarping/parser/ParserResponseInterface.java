package com.example.petrolstation.web.scarping.parser;

import com.example.petrolstation.models.FuelPrice;

import java.util.ArrayList;

public interface ParserResponseInterface {
    void onParsingDone(ArrayList<FuelPrice> fuelPriceArrayList);
}
