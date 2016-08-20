package com.example.venki.up.activities.housing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.venki.up.R;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Retrofit;

public class HousingActivity extends AppCompatActivity {

    private static final String TAG = "HOUSING_FRAGMENT";

    @Inject @Named("Housing") Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housing);
    }
}
