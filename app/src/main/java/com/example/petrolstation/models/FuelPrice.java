package com.example.petrolstation.models;

public class FuelPrice {
    private int id;
    private String effectiveDate;
    private String effectiveTime;
    private String petrolPrice;
    private String dieselPrice;
    private String kerosenePrice;
    private String lpgPrice;
    private String atfDp;
    private String atfDf;

    public static final String TABLE_NAME = "fuelPrice";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EFFECTIVE_DATE = "effectiveDate";
    public static final String COLUMN_EFFECTIVE_TIME = "effectiveTime";
    public static final String COLUMN_PETROL_PRICE = "petrolPrice";
    public static final String COLUMN_DIESEL_PRICE = "dieselPrice";
    public static final String COLUMN_KEROSENE_PRICE = "kerosenePrice";
    public static final String COLUMN_LPG_PRICE = "lpgPrice";
    public static final String COLUMN_ATFDP_PRICE = "atfDP";
    public static final String COLUMN_ATFDF_PRICE = "atfDf";


    // SQL Query for table creation
    public static final String sqlQuery = "CREATE TABLE " + TABLE_NAME + "(\n" +
            "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`effectiveDate`\tTEXT,\n" +
            "\t`effectiveTime`\tTEXT,\n" +
            "\t`petrolPrice`\tTEXT,\n" +
            "\t`dieselPrice`\tTEXT,\n" +
            "\t`kerosenePrice`\tTEXT,\n" +
            "\t`lpgPrice`\tTEXT,\n" +
            "\t`atfDP`\tTEXT,\n" +
            "\t`atfDf`\tTEXT,\n" +
            "\t`timestamp`\tDATETIME DEFAULT CURRENT_TIMESTAMP\n" +
            ");";

    public FuelPrice(int id, String effectiveDate, String effectiveTime,
                     String petrolPrice, String dieselPrice,
                     String kerosenePrice, String lpgPrice, String atfDp, String atfDf) {
        this.id = id;
        this.effectiveDate = effectiveDate;
        this.effectiveTime = effectiveTime;
        this.petrolPrice = petrolPrice;
        this.dieselPrice = dieselPrice;
        this.kerosenePrice = kerosenePrice;
        this.lpgPrice = lpgPrice;
        this.atfDp = atfDp;
        this.atfDf = atfDf;
    }

    public FuelPrice(String effectiveDate, String effectiveTime, String petrolPrice,
                     String dieselPrice, String kerosenePrice, String lpgPrice,
                     String atfDp, String atfDf) {
        this.effectiveDate = effectiveDate;
        this.effectiveTime = effectiveTime;
        this.petrolPrice = petrolPrice;
        this.dieselPrice = dieselPrice;
        this.kerosenePrice = kerosenePrice;
        this.lpgPrice = lpgPrice;
        this.atfDp = atfDp;
        this.atfDf = atfDf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAtfDf() {
        return atfDf;
    }

    public void setAtfDf(String atfDf) {
        this.atfDf = atfDf;
    }
}
