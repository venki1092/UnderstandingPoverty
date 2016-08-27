package com.app.venki.up;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.venki.up.model.Post;
import com.firebase.client.Firebase;

import java.util.Date;

/**
 * Created by bala on 8/5/16.
 */
public class HelpingHandPostActivity extends AppCompatActivity {
    private Button postButton;
    private EditText editText;
    private String email_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helping_hand_post);
        email_address = getIntent().getStringExtra(LoginActivity.EXTRA_MESSAGE);
        postButton = (Button) findViewById(R.id.post_detail_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText = (EditText) findViewById(R.id.post_data);
                String data = editText.getText().toString();
                pushData(data);
                finishActivity(100);
            }
        });

    }

    private void pushData(String data)
    {
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(FirebaseConstants.POST_URL);
        Post post = new Post();
        post.setEmail(email_address);
        post.setPost(data);
        post.setDate(new Date());
        ref.push().setValue(post);
    }
}
