package com.example.venki.up;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.venki.up.adapters.PostListAdapter;
import com.example.venki.up.model.Post;

/**
 * Created by bala on 8/5/16.
 */
public class HelpingHandMyrequestsActivity extends AppCompatActivity {
    private RecyclerView postList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helping_hand_myrequests);

        postList = (RecyclerView)findViewById(R.id.post_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        postList.setLayoutManager(mLayoutManager);
        PostListAdapter pla = new PostListAdapter();
        pla.setListener(new PostListAdapter.OnPostClickListener() {
            @Override
            public void onPostClick(Post post) {
                Log.d("MyRequests",post.getPost());
            }
        });
        postList.setAdapter(pla);

    }
}
