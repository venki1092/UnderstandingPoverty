package com.example.venki.up.activities.coupons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.venki.up.activities.WebViewActivity;
import com.example.venki.up.providers.CouponsGroupOn;
import com.example.venki.up.R;
import com.example.venki.up.models.events.coupons.GroupOnEvent;
import com.example.venki.up.providers.CouponsGroupOn;
import com.example.venki.up.recycleradapters.CouponsRecyclerAdapter;
import com.example.venki.up.recycleradapters.GroupOnRecycler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

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
    private  String division = "chicago";
    private String category ="category:food-and-drink";
    private String offset ="0";
    private  int limit = 50;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons2);

        final Spinner spinner = (Spinner) findViewById(R.id.category_spinner);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.coupon_recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        retrofit();
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
        Call<ResponseBody> call = couponService.getCouponsGroupOn(token,division,category,offset,limit);
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
                    if (recyclerView != null) {

                    }
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
}


