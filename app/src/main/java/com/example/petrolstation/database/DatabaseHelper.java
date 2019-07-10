package com.example.petrolstation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.petrolstation.models.FuelPrice;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "petrol_station_db";

    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE Table fuelprice
        db.execSQL(FuelPrice.sqlQuery);
        Log.d(TAG, "Created Table : " + FuelPrice.TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + FuelPrice.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertFuelDetail(FuelPrice fuelPrice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FuelPrice.COLUMN_EFFECTIVE_DATE, fuelPrice.getEffectiveDate());
        contentValues.put(FuelPrice.COLUMN_EFFECTIVE_TIME, fuelPrice.getEffectiveTime());
        contentValues.put(FuelPrice.COLUMN_PETROL_PRICE, fuelPrice.getPetrolPrice());
        contentValues.put(FuelPrice.COLUMN_DIESEL_PRICE, fuelPrice.getDieselPrice());
        contentValues.put(FuelPrice.COLUMN_KEROSENE_PRICE, fuelPrice.getKerosenePrice());
        contentValues.put(FuelPrice.COLUMN_LPG_PRICE, fuelPrice.getLpgPrice());
        contentValues.put(FuelPrice.COLUMN_ATFDP_PRICE, fuelPrice.getAtfDp());
        contentValues.put(FuelPrice.COLUMN_ATFDF_PRICE, fuelPrice.getAtfDf());
        // timestamp date will be added automatically;

        long id = db.insert(FuelPrice.TABLE_NAME, null, contentValues);

        // close db connection
        db.close();

        Log.d(TAG, "InsertedFuelDetail: " + fuelPrice.getEffectiveDate());
        return id;
    }

    public FuelPrice getFuelPriceDetail(long id) {

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FuelPrice.TABLE_NAME,
                new String[]{FuelPrice.COLUMN_ID, FuelPrice.COLUMN_EFFECTIVE_DATE,
                        FuelPrice.COLUMN_EFFECTIVE_TIME, FuelPrice.COLUMN_PETROL_PRICE,
                        FuelPrice.COLUMN_DIESEL_PRICE, FuelPrice.COLUMN_KEROSENE_PRICE,
                        FuelPrice.COLUMN_LPG_PRICE, FuelPrice.COLUMN_ATFDP_PRICE,
                        FuelPrice.COLUMN_ATFDF_PRICE
                },
                id + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        FuelPrice fuelPrice = new FuelPrice(
                cursor.getInt(cursor.getColumnIndex(FuelPrice.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_EFFECTIVE_DATE)),
                cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_EFFECTIVE_TIME)),
                cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_PETROL_PRICE)),
                cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_DIESEL_PRICE)),
                cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_KEROSENE_PRICE)),
                cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_DIESEL_PRICE)),
                cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_ATFDP_PRICE)),
                cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_ATFDF_PRICE))
        );

        // close the db connection
        cursor.close();

        return fuelPrice;
    }

    public ArrayList<FuelPrice> getAllFuelPriceDetail() {
        // Select All Query
//        String selectQuery = "SELECT  * FROM " + FuelPrice.TABLE_NAME + " ORDER BY " +
//                FuelPrice.COLUMN_ID + " DESC";

        String selectQuery = "SELECT  * FROM " + FuelPrice.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<FuelPrice> fuelPriceList = new ArrayList<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FuelPrice fuelPrice = new FuelPrice(
                        cursor.getInt(cursor.getColumnIndex(FuelPrice.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_EFFECTIVE_DATE)),
                        cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_EFFECTIVE_TIME)),
                        cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_PETROL_PRICE)),
                        cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_DIESEL_PRICE)),
                        cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_KEROSENE_PRICE)),
                        cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_DIESEL_PRICE)),
                        cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_ATFDP_PRICE)),
                        cursor.getString(cursor.getColumnIndex(FuelPrice.COLUMN_ATFDF_PRICE))
                );
                fuelPriceList.add(fuelPrice);
            } while (cursor.moveToNext());
        }
        db.close();

        return fuelPriceList;
    }

    public void cleanTable() {
        String truncateQuery = "DELETE FROM " + FuelPrice.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        // delete all the record
        db.execSQL(truncateQuery);

        Log.d(TAG, "cleanTable(): ");
        db.close();

    }

    public int getFuelPriceCount() {
        String countQuery = "SELECT  * FROM " + FuelPrice.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        Log.d(TAG, "getFuelPriceCount: " + count);
        cursor.close();


        // return count
        return count;
    }

    public boolean isFuelPriceDetailEmpty() {
        // if fuel pricecount() == 0 return true else false
        return getFuelPriceCount() != 0? false : true;
    }

    public boolean isTableExists(String tableName, boolean openDb) {
        if(openDb) {
            if(mDatabase == null || !mDatabase.isOpen()) {
                mDatabase = getReadableDatabase();
            }

            if(!mDatabase.isReadOnly()) {
                mDatabase.close();
                mDatabase = getReadableDatabase();
            }
        }

        Cursor cursor = mDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}
