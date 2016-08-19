package com.example.venki.up.clickListener;

import android.view.View;

/**
 * Created by Billy on 8/9/16.
 */
public interface RecyclerClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
