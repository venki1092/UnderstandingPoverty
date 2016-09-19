package com.app.venki.up;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.app.venki.up.activities.AboutUPActivity;
import com.app.venki.up.activities.coupons.Coupons;
import com.app.venki.up.activities.housing.HousingActivity;
import com.app.venki.up.activities.jobs.JobsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LandingPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private final int PERMISSION_ACCESS_COARSE_LOCATION = 22;
    private final String RESULT_DATA_KEY = "UP.RESULT_DATA_KEY";
    private final int SUCCESS_RESULT = 0;
    private final int FAILURE_RESULT = 1;

    private String email_address;
    private GridView gridView;

    private GoogleApiClient googleApiClient;
    private static Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        email_address = getIntent().getStringExtra(LoginActivity.EXTRA_MESSAGE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_up);


        getSupportActionBar().setTitle("HELLO");

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new LandingPageImageAdapter(this));
        setGridViewClicker();


    }


    private void setGridViewClicker(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Toast.makeText(LandingPage.this, "" + position, Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    Intent intent = new Intent(LandingPage.this, Coupons.class);
                    startActivity(intent);
                }
                if (position == 1) {
                    //discount feature
                    Toast.makeText(LandingPage.this, "feature coming soon", Toast.LENGTH_SHORT).show();
                }
                if (position == 2) {
                    Intent intent = new Intent(LandingPage.this, JobsActivity.class);
                    startActivity(intent);
                }
                if (position == 3) {
                    //helping feature
                    Toast.makeText(LandingPage.this, "feature coming soon", Toast.LENGTH_SHORT).show();
                }
                if (position == 4) {
                    Intent intent = new Intent(LandingPage.this, HousingActivity.class);
                    startActivity(intent);
                }
                if (position == 5) {
                    Intent intent = new Intent(LandingPage.this, AboutUPActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_job) {
            Intent intent = new Intent(LandingPage.this, JobsActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_discounts) {

        }else if (id == R.id.nav_coupon){
            Intent intent = new Intent(LandingPage.this, Coupons.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_helping_hand) {
            Toast.makeText(LandingPage.this, "Feature comming soon", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(LandingPage.this,HelpingHandActivity.class);
//            intent.putExtra(LoginActivity.EXTRA_MESSAGE,email_address);
//            startActivity(intent);
        } else if (id == R.id.nav_job) {


        } else if (id == R.id.nav_housing){
            Log.d("Housing", "Housing nav clicked");
            Intent intent = new Intent(LandingPage.this, HousingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
