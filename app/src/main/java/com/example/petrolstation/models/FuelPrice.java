package com.example.petrolstation.models;

import java.util.Date;

public class FuelPrice {
    private String effectiveDate;
    private String effectiveTime;
    private String petrolPrice;
    private String dieselPrice;
    private String kerosenePrice;
    private String lpgPrice;
    private String atfDp;
    private String arfDf;

    public FuelPrice(String effectiveDate, String effectiveTime, String petrolPrice,
                     String dieselPrice, String kerosenePrice, String lpgPrice,
                     String atfDp, String arfDf) {
        this.effectiveDate = effectiveDate;
        this.effectiveTime = effectiveTime;
        this.petrolPrice = petrolPrice;
        this.dieselPrice = dieselPrice;
        this.kerosenePrice = kerosenePrice;
        this.lpgPrice = lpgPrice;
        this.atfDp = atfDp;
        this.arfDf = arfDf;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(String petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getDieselPrice() {
        return dieselPrice;
    }

    public void setDieselPrice(String dieselPrice) {
        this.dieselPrice = dieselPrice;
    }

    public String getKerosenePrice() {
        return kerosenePrice;
    }

    public void setKerosenePrice(String kerosenePrice) {
        this.kerosenePrice = kerosenePrice;
    }

    public String getLpgPrice() {
        return lpgPrice;
    }

    public void setLpgPrice(String lpgPrice) {
        this.lpgPrice = lpgPrice;
    }

    public String getAtfDp() {
        return atfDp;
    }

    public void setAtfDp(String atfDp) {
        this.atfDp = atfDp;
    }

    public String getArfDf() {
        return arfDf;
    }

    public void setArfDf(String arfDf) {
        this.arfDf = arfDf;
    }
}
