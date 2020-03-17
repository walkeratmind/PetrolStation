package com.example.petrolstation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.petrolstation.helper.SettingHelper;

public class SettingActivity extends AppCompatActivity {

    Spinner mapStyle;

    private static final String TAG = SettingActivity.class.getSimpleName();

    private static final String SELECTED_STYLE = "selected_style";

    SettingHelper settingHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mapStyle = findViewById(R.id.map_style);

        settingHelper = new SettingHelper();

//        ArrayAdapter<> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_dropdown_item, settingHelper.mStyleIds);


    }
}
