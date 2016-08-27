package com.example.venki.up.activities.jobs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.venki.up.LandingPage;
import com.example.venki.up.R;
import com.example.venki.up.Utilities.UpApplication;
import com.example.venki.up.activities.WebViewActivity;
import com.example.venki.up.adapters.JobsRVAdapter;
import com.example.venki.up.model.job.Indeed;
import com.example.venki.up.providers.IndeedService;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JobsActivity extends AppCompatActivity implements JobsRVAdapter.JobClickListener{

    private static final String TAG = "JOBS_FRAGMENT";
    private Button submitButton;
    private EditText cityEditText;
    private EditText jobTitleEditText;
    private RecyclerView jobRecyclerView;
    private SwipeRefreshLayout jobsSwipeRefreshLayout;
    private Indeed indeed;

    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private JobsRVAdapter jobsRVAdapter;
    SharedPreferences sharedPreferences;

    @Inject
    @Named("Jobs")
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        initViews();
        setLayoutManger();
        getSharedPreferences();

        injectDagger();

        displaySharedPreferences();

        swipeJobsRefreshListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        submitButton = (Button)findViewById(R.id.job_activity_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobsApiCall();
            }
        });
    }

    private void initViews(){
        cityEditText = (EditText)findViewById(R.id.job_city_editText);
        jobTitleEditText = (EditText)findViewById(R.id.job_title_editText);
        jobRecyclerView = (RecyclerView)findViewById(R.id.job_recyclerView);
        jobsSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.job_swipeRefreshLayout);

        jobsSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimary);
    }

    private void setLayoutManger(){
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
    }

    private void getSharedPreferences(){
        sharedPreferences = getSharedPreferences("JOBS", Context.MODE_PRIVATE);
    }

    private void swipeJobsRefreshListener(){
        jobsSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshJobsContent();
            }
        });
    }

    private void injectDagger(){
        ((UpApplication)getApplication()).getNetComponent().inject(this);
    }

    private void displaySharedPreferences(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Indeed","");

        if(json != ""){
            Indeed indeed = gson.fromJson(json, Indeed.class);
            jobRecyclerView.setLayoutManager(linearLayoutManager);
            //jobRecyclerView.setLayoutManager(gridLayoutManager);
            jobsRVAdapter = new JobsRVAdapter(this, indeed.getResults());
            jobRecyclerView.setAdapter(jobsRVAdapter);
        }
    }

    /**
     * Pull down to refresh will make new API call
     */
    private void refreshJobsContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jobsApiCall();
                jobsSwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
    }

    private void jobsApiCall() {

        IndeedService service = retrofit.create(IndeedService.class);

        String apiKey = "7516191153543229";
        String query = jobTitleEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String country = "us";
        String limit = "40";
        String latLong = "";
        String version = "2";
        String format = "json";

        Call<Indeed> call = service.getIndeedJobs(apiKey, query, city, country, limit, latLong, version, format);
        call.enqueue(new Callback<Indeed>() {
            @Override
            public void onResponse(Call<Indeed> call, Response<Indeed> response) {
                if(response.isSuccessful()){
                    indeed = response.body();
                    // String title = indeed.getResults().get(0).getJobtitle();

                    if(jobRecyclerView != null){
                        jobRecyclerView.setLayoutManager(linearLayoutManager);
                        jobsRVAdapter = new JobsRVAdapter(JobsActivity.this, indeed.getResults());
                        jobRecyclerView.setAdapter(jobsRVAdapter);
                    }

                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(indeed);
                    prefsEditor.putString("Indeed", json);
                    prefsEditor.commit();
                }
            }

            @Override
            public void onFailure(Call<Indeed> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onCardViewClick(String link) {
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home_page) {

            Intent intent = new Intent(JobsActivity.this, LandingPage.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
