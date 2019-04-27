package com.example.petrolstation;

import android.app.Dialog;
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
import android.view.MenuItem;
import android.widget.Toast;

import com.example.petrolstation.fragments.DetailsFragment;
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


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Object dataTransfer[] = new Object[2];
            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();

            Fragment fragment = null;
            FragmentManager fragmentManager = getSupportFragmentManager();

            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    fragment = new MapsFragment();
                    break;
//                    return true;
                case R.id.navigation_gas_station:
//                    mMap.clear();
//                    String petrolPump = "gas_station";
//
//                    String url = getUrl(latitude, longitude, petrolPump);
//                    dataTransfer[0] = mMap;
//                    dataTransfer[1] = url;
//
//                    getNearbyPlacesData.execute(dataTransfer);
//                    Toast.makeText(MapsActivity.this, "Showing nearby gas stations",
//                            Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_setting:
                    fragment = new DetailsFragment();
                    break;
            }

//            fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // view Binding here...
        drawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.side_navigation_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);

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
            initFragment();
//            this.finish();
        }

    }

    private void init() {

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    private void initFragment() {

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        fragmentManager.beginTransaction().add(R.id.main_container, detailsFragment, "3")
//                .hide(detailsFragment).commit();
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

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new NavigationView.OnNavigationItemSelectedListener() {

                Fragment fragment = null;

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_map:
                            fragment = new MapsFragment();
                            break;
                        case R.id.nav_location:
                            fragment = new DetailsFragment();
                            break;
                        case R.id.nav_price:
                            fragment = new DetailsFragment();
                            break;
                        case R.id.nav_setting:
                            fragment = new DetailsFragment();
                            break;
                        case R.id.nav_share:
                            startActivity(new Intent(MainActivity.this, MapsActivity.class));
                            fragment = new DetailsFragment();
                            break;
                        default:

                    }

                    fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();

                    //close the drawer
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            };
}
