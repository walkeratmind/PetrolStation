package com.example.petrolstation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.petrolstation.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View mWindow;
    private Context context;

    public CustomInfoWindowAdapter(View mWindow, Context context) {
        this.context = context;
        this.mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

   public void renderWindowText(Marker marker, View view) {
        String title = marker.getTitle();
       TextView tvTitle = (TextView) view.findViewById(R.id.title);

       if(!title.equals("")){
           tvTitle.setText(title);
       }

       String snippet = marker.getSnippet();
       TextView tvSnippet = (TextView) view.findViewById(R.id.snippet);

       if(!snippet.equals("")){
           tvSnippet.setText(snippet);
       }
   }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}
