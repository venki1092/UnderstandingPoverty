package com.app.venki.up;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.landing_page_custom_grid, null);
            TextView textView = (TextView)grid.findViewById(R.id.grid_textID);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_imageID);
//            imageView.setLayoutParams(new GridView.LayoutParams(520,520));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
            imageView.setImageResource(mThumbIds[position]);
            textView.setText(title[position]);

        } else {
            grid = (View) convertView;
        }



        return grid;
    }

    private String[] title = {
            "Events/Coupons", "Discounts", "Jobs", "Help", "Housing", "About UP"
    };

    // references to our images
    private Integer[] mThumbIds = {
            R.mipmap.events, R.mipmap.discount,
            R.mipmap.job, R.mipmap.help,
            R.mipmap.find_house, R.mipmap.ic_up
//            R.mipmap.job, R.mipmap.help
    };
}