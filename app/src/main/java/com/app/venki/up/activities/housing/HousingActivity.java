package com.app.venki.up.activities.housing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.venki.up.LandingPage;
import com.app.venki.up.R;
import com.app.venki.up.Utilities.UpApplication;
import com.app.venki.up.Utilities.UtilityFunctions;
import com.app.venki.up.Utilities.findLocation.Constants;
import com.app.venki.up.activities.WebViewActivity;
import com.app.venki.up.adapters.HousingRVAdapter;
import com.app.venki.up.model.housing.HousingHUD;
import com.app.venki.up.providers.HousingService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HousingActivity extends AppCompatActivity
        implements HousingRVAdapter.HousingClickListener {

    private static final String TAG = "HOUSING_FRAGMENT";

    private Button submitButton;
    private EditText cityEditText;
    private RecyclerView housingRecyclerView;
    private SwipeRefreshLayout housingSwipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private HousingRVAdapter housingRVAdapter;
    SharedPreferences sharedPreferences;
    private static Address address;


    @Inject @Named("Housing") Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housing);

        initViews();
        setLayoutManager();
        getSharedPreferences();
        injectDagger();
        displaySharedPreferences();
        swipeHousingRefreshListener();

        getSupportActionBar().setTitle(getIntent().getExtras().getString(Constants.LOCALITY));
    }


    private void initViews(){
        cityEditText = (EditText)findViewById(R.id.housing_city_editText);
        housingRecyclerView = (RecyclerView)findViewById(R.id.housing_recyclerView);
        housingSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.housing_swipeRefreshLayout);
        housingSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimary);
    }

    private void setLayoutManager(){
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
    }

    private void getSharedPreferences(){
        sharedPreferences = getSharedPreferences("HOUSING", Context.MODE_PRIVATE);
    }

    private void injectDagger(){
        ((UpApplication)getApplication()).getNetComponent().inject(this);
    }

    private void swipeHousingRefreshListener(){
        housingSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHousingContent();
            }
        });
    }

    private void displaySharedPreferences(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("HousingHUD","");
        if(json != ""){
            HousingHUD housingHUD = gson.fromJson(json, HousingHUD.class);
            housingRecyclerView.setLayoutManager(linearLayoutManager);
            //housingRecyclerView.setLayoutManager(gridLayoutManager);
            housingRVAdapter = new HousingRVAdapter(this, housingHUD);
            housingRecyclerView.setAdapter(housingRVAdapter);
        }
    }

    /**
     * Pull down to refresh will make new API call
     */
    private void refreshHousingContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                housingApiCall();
                housingSwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        submitButton = (Button)findViewById(R.id.housing_activity_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilityFunctions.hideSoftKeyboard(HousingActivity.this);
                housingApiCall();
            }
        });
    }

    private Address getLatLng() throws IOException{
        Geocoder gc = new Geocoder(this);
        String location = cityEditText.getText().toString();
        List<Address> address = gc.getFromLocationName(location,5);
        Log.d(TAG, "Locality: " + address.get(0).getLocality());
        Log.d(TAG, "Latitude: "+address.get(0).getLatitude());
        Log.d(TAG, "Longitude: "+address.get(0).getLongitude());
        return address.get(0);
    }

    private void housingApiCall(){

        try {
            address = getLatLng();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HousingService service = retrofit.create(HousingService.class);

        String latitude = Double.toString(address.getLatitude());
        String longitude = Double.toString(address.getLongitude());
        String distance = "50";
        String rowLimit = "";
        String services = "";
        String languages = "";

        Call<ResponseBody> call = service.getHousingAgencies(latitude, longitude, distance, rowLimit, services, languages);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String parsedRespone = null;
                try {
                    parsedRespone = response.body().string().replace("[", "{\"housing\" : [").replace("]", "]}");
                    Gson gson = new Gson();
                    HousingHUD housingHUD = gson.fromJson(parsedRespone, HousingHUD.class);

                    if(housingRecyclerView != null){
                        housingRecyclerView.setLayoutManager(linearLayoutManager);
                        housingRVAdapter = new HousingRVAdapter(HousingActivity.this, housingHUD);
                        housingRecyclerView.setAdapter(housingRVAdapter);
                    }

                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    Gson gsonHousing = new Gson();
                    String json = gsonHousing.toJson(housingHUD);
                    prefsEditor.putString("HousingHUD", json);
                    prefsEditor.commit();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onCardViewClick(String link) {
        if(link == null || link.equals("n/a")){
            return;
        }
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home_page) {
            Intent intent = new Intent(HousingActivity.this, LandingPage.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
