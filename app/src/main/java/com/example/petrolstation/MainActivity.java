package com.example.petrolstation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.petrolstation.fragments.DetailsFragment;
import com.example.petrolstation.fragments.GasStationListFragment;
import com.example.petrolstation.fragments.MapsFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import static com.example.petrolstation.Constants.ERROR_DIALOG_REQUEST;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "Main Activity";

    BottomNavigationView bottomNavigationView;

    Fragment detailsFragment = new DetailsFragment();
    Fragment mapFragment = new MapsFragment();
    Fragment active = mapFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // view Binding here...
        drawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.side_navigation_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        if (isServicesOk()) {
//            init();
            fragmentManager.beginTransaction().replace(R.id.main_container, mapFragment).commit();
//            this.finish();
        }

    }

    private void init() {

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    // Get toolbar for Map fragment
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbar);
    }


    private boolean isServicesOk() {
        Log.d(TAG, "isServicesOK: Checking google Services Version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS) {
            // Everything is cool and user can request for map
            Log.d(TAG, "isServicesOK:    Google Play Services is working ");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
//            an error is occured but can be fixed
            Log.d(TAG, "isServicesOk: an error occured but it can be fixed");

            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();

        } else {
            Toast.makeText(this, "can't request for map", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
//        exitByBackKey();

        // goto map fragment
        Fragment fragment = new MapsFragment();
        fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();

        //close the drawer
        drawerLayout.closeDrawer(GravityCompat.START);
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exitByBackKey();
//
//            //moveTaskToBack(false);
//
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }


    public void shareText(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Your shearing message goes here";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new NavigationView.OnNavigationItemSelectedListener() {

                Fragment fragment = null;

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_map:
                            fragment = new MapsFragment();
                            break;
                        case R.id.nav_station_list:
                            fragment = new GasStationListFragment();
                            break;
                        case R.id.nav_price:
                            fragment = new DetailsFragment();
                            break;
                        case R.id.nav_add_station:
                            startActivity(new Intent(MainActivity.this, SuggestionActivity.class));
                            return true;
                        case R.id.nav_about:
                            startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                            return true;
                            // return here so no any fragment transaction is done
                        case R.id.nav_share:
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBodyText = "Petrol Station , Travel Less For Fuel,\nplaystore download link here...";
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "About Petrol Station");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));

                            return true;
                        default:

                    }

                    fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();

                    //close the drawer
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            };
}
