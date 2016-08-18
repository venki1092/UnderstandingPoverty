package com.example.venki.up.activities.jobs;

import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.example.venki.up.R;
import com.example.venki.up.adapters.JobsRVAdapter;
import com.example.venki.up.model.job.Indeed;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Retrofit;

public class JobsActivity extends AppCompatActivity {

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
    }
}
