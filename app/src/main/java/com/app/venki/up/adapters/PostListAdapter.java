package com.app.venki.up.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.venki.up.R;
import com.app.venki.up.model.Post;
import com.app.venki.up.model.Singleton;


/**
 * Created by bala on 8/5/16.
 */
public class PostListAdapter extends RecyclerView.Adapter{

    private OnPostClickListener listener;

    public OnPostClickListener getListener() {
        return listener;
    }

    public void setListener(OnPostClickListener listener) {
        this.listener = listener;
    }

    public interface OnPostClickListener {
        void onPostClick(Post post);
    }

    public class PostListHolder extends RecyclerView.ViewHolder {
        public TextView post;
        private View view;
        public PostListHolder(View itemView) {
            super(itemView);
            view = itemView;
            post = (TextView)itemView.findViewById(R.id.post_text);
        }

        public void setListener(final Post post)
        {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onPostClick(post);
                }
            });
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_helping_hand_myrequests_row,parent,false);
        return new PostListHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = Singleton.getInstance().postList.get(position);
        ((PostListHolder)holder).post.setText(post.getPost());
    }

    @Override
    public int getItemCount() {
        return Singleton.getInstance().postList.size();
    }
}
