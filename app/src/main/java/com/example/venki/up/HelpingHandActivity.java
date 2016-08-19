package com.example.venki.up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.venki.up.model.Post;
import com.example.venki.up.model.Singleton;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.Date;

/**
 * Created by bala on 8/3/16.
 */
public class HelpingHandActivity extends AppCompatActivity{
    private Button postRequest ;
    private Button myRequests ;
    private String email_address;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helping_hand);
        email_address = getIntent().getStringExtra(LoginActivity.EXTRA_MESSAGE);
        postRequest = (Button) findViewById(R.id.post_button);
        myRequests = (Button) findViewById(R.id.myrequest_button);

        postRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpingHandActivity.this,HelpingHandPostActivity.class);
                intent.putExtra(LoginActivity.EXTRA_MESSAGE,email_address);
                startActivity(intent);
            }
        });

        myRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HelpingHand", "My Requests");
                Intent intent = new Intent(HelpingHandActivity.this,HelpingHandMyrequestsActivity.class);
                intent.putExtra(LoginActivity.EXTRA_MESSAGE,email_address);
                startActivity(intent);
            }
        });

        retrieveMyPosts();

    }


    public void retrieveMyPosts()
    {
        Firebase.setAndroidContext(this);
        Firebase firebase = new Firebase(FirebaseConstants.POST_URL);
        Query query = firebase.orderByChild("email").equalTo(email_address);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post post = new Post();
                post.setId(dataSnapshot.getKey());
                post.setPost(dataSnapshot.child("post").getValue().toString());
                String d = dataSnapshot.child("date").getValue().toString();
                long l = Long.parseLong(d);
                post.setDate(new Date(l));
                post.setEmail(dataSnapshot.child("email").getValue().toString());
                Singleton.getInstance().postList.add(post);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}

