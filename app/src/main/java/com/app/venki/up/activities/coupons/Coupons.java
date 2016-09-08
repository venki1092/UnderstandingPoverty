package com.app.venki.up.activities.coupons;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.venki.up.LandingPage;
import com.app.venki.up.Utilities.Utilities;
import com.app.venki.up.activities.WebViewActivity;
import com.app.venki.up.model.Category;
import com.app.venki.up.providers.CouponsGroupOn;
import com.app.venki.up.R;
import com.app.venki.up.models.events.coupons.GroupOnEvent;
import com.app.venki.up.recycleradapters.GroupOnRecycler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Coupons extends AppCompatActivity implements GroupOnRecycler.CouponClickListener2 {

    private static final String TAG = "COUPON_FRAGMENT";
    private CouponsGroupOn couponService;
    private GroupOnRecycler couponsRecyclerAdapter;
    private RecyclerView recyclerView;
    private GroupOnEvent couponsList;
    //https://partner-api.groupon.com/deals.json?tsToken=US_AFF_0_201236_212556_0&division_id=chicago&filters=category:food-and-drink&offset=0&limit=50
    private String couponHTTP = "https://partner-api.groupon.com/";
    private  String token = "US_AFF_0_201236_212556_0";
    private  String division = "san-jose";
    private String categorySearch ="category:food-and-drink";
    private String offset ="0";
    private int limit = 30;
    private EditText place;
    private Button searchCoupons;
    private Spinner spinner;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayAdapter<Category> adapter;
    private ArrayList<Category> categoryData;

    public String formatString(String inputString){
        String outputString = inputString.toLowerCase();
        outputString = outputString.trim();
        outputString = outputString.replaceAll("\\s+","-");
        //Log.i("string-test",outputString.substring(outputString.length() - 1));
        return outputString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons2);
        setViews();
        setCategoryData();
        spinner.setAdapter(adapter);
        setSearchCoupon();
        setSpinner();
        recyclerView = (RecyclerView) findViewById(R.id.coupon_recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //retrofit();
        setSwipeRefreshLayout();
    }

    private void setViews(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.coupon_swipeRefreshLayout);
        place = (EditText) findViewById(R.id.place);
        place.setHint("City");
        searchCoupons = (Button) findViewById(R.id.couponsearch);
        spinner = (Spinner) findViewById(R.id.category_spinner);
    }

    private void setCategoryData(){
        categoryData = new ArrayList<>();
        categoryData.add(new Category("food-and-drink","Food and Drinks"));
        categoryData.add(new Category("health-and-fitness","Health and Fitness"));
        categoryData.add(new Category("things-to-do","Things to Do"));
//        categoryData.add(new Category("shopping","Shopping"));
//        categoryData.add(new Category("local-services","Local Services"));
        categoryData.add(new Category("home-improvement","Home Improvement"));
        categoryData.add(new Category("beauty-and-spas","Beauty and Spas"));
//        categoryData.add(new Category("automotive","Automotive"));
        categoryData.add(new Category("baby-kids-and-toys","Baby Kids and Toys"));
        categoryData.add(new Category("electronics","Electronics"));
        categoryData.add(new Category("entertainment-and-media","Entertainment and Media"));
//        categoryData.add(new Category("food-and-drink-goods","Food and Drink goods"));
        categoryData.add(new Category("health-and-beauty-goods","Health and Beauty Goods"));
        categoryData.add(new Category("home-and-garden","Home and Garden"));
        categoryData.add(new Category("household-essentials","Household Essentials"));
        categoryData.add(new Category("jewelry-and-watches","Jewelry and Watches"));
//        categoryData.add(new Category("men","Men"));
        categoryData.add(new Category("sports-and-outdoors","Sports and Outdoors"));
//        categoryData.add(new Category("women","Women"));
//        categoryData.add(new Category("accommodation","Accommodation"));
        categoryData.add(new Category("bed-and-breakfast-travel","Bed and Breakfast Travel"));
        categoryData.add(new Category("cabin-travel","Cabin Travel"));
        categoryData.add(new Category("cruise-travel","Cruise Travel"));
        categoryData.add(new Category("hotels","Hotels"));
        categoryData.add(new Category("resort-travel","Resort Travel"));
        categoryData.add(new Category("tour-travel","Tour Travel"));
        categoryData.add(new Category("vacation-rental-travel","Vacation Rental Travel"));
        adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item, categoryData);
    }

    private void setSearchCoupon(){
        searchCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.hideSoftKeyboard(Coupons.this);
                String placeValue = String.valueOf(place.getText());
                if(placeValue.isEmpty() || placeValue == null){
                    Toast.makeText(getApplicationContext(),"Please provide the location details",Toast.LENGTH_LONG).show();
                    return;
                }
                String userCityInput = formatString(String.valueOf(place.getText()));
                division = userCityInput;
//                Log.i("division", "city is: " + division);
                retrofit();
            }
        });
    }

    private void setSpinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getSelectedItem();
                categorySearch ="category:" + category.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrofit();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void retrofit(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        GsonBuilder gsonBuilder = new GsonBuilder().setLenient();
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(couponHTTP)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        couponService = retrofit.create(CouponsGroupOn.class);
        Call<ResponseBody> call = couponService.getCouponsGroupOn(token,division,categorySearch,offset,limit);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String parsedRespone = null;
                try {
                    //parsedRespone = response.body().string().replace("[","{\"coupons\" : [").replace("]","]}");
                    parsedRespone = response.body().string();
                    Gson gson = new Gson();

                    GroupOnEvent couponsData = gson.fromJson(parsedRespone, GroupOnEvent.class);
                    if (couponsData == null) {
                        return;
                    }
                    couponsRecyclerAdapter = new GroupOnRecycler(Coupons.this,couponsData);
                    recyclerView.setAdapter(couponsRecyclerAdapter);
                    couponsRecyclerAdapter.notifyDataSetChanged();
                    //Collections.addAll(couponsList, couponsData);
                    //                Log.i(TAG, " "+ couponsList);
                    if (recyclerView != null) {}
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

    /*
    AdapterView.OnItemSelectedListener
   public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Log.i("yash", (String) parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }*/

    @Override
    public void onCardViewClick(String link) {
        Intent intent = new Intent(this, WebViewActivity.class);

        intent.putExtra("link", link);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home_page) {

            Intent intent = new Intent(Coupons.this, LandingPage.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


