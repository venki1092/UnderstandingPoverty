package com.example.venki.up.recycleradapters;

/**
 * Created by yash on 8/20/2016.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.venki.up.R;
import com.example.venki.up.models.events.coupons.CouponsArray;
import com.example.venki.up.models.events.coupons.GroupOnEvent;

import okhttp3.ResponseBody;
import retrofit2.Callback;


public class GroupOnRecycler extends android.support.v7.widget.RecyclerView.Adapter<GroupOnRecycler.RecyclerViewHolder> {

    private GroupOnEvent data;
    private Context context;
    private CouponClickListener2 couponClickListener;



    public interface CouponClickListener2{
        void onCardViewClick(String link);
    }


    // this is where we setup TextView
    public class RecyclerViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        CardView couponCardView;
        ImageView imageView;
        TextView title;
        TextView info;

        public RecyclerViewHolder (final View itemView) {
            super(itemView);
            couponCardView = (CardView)itemView.findViewById(R.id.custom_cardView);
            imageView = (ImageView) itemView.findViewById(R.id.imageOne_id);
            title = (TextView) itemView.findViewById(R.id.title_id);
            info = (TextView) itemView.findViewById(R.id.info_ID);
        }

        public void bind(final CouponClickListener2 couponClickListener, final String link){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    couponClickListener.onCardViewClick(link);
                }
            });
        }
    }


    public GroupOnRecycler(CouponClickListener2 couponClickListener, GroupOnEvent data) {
        this.couponClickListener =  couponClickListener;
        this.data = data;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Log.i("yash", String.valueOf(data.getDeals().size()));
        Log.i("yash", String.valueOf(data.getDeals().get(position).getResults().size()));
        holder.title.setText(data.getDeals().get(0).getResults().get(position).getTitle());
        //holder.info.setVisibility(View.GONE);

        String url =data.getDeals().get(0).getResults().get(position).getDealURL();
        holder.bind(couponClickListener, url);

        String imageURI =data.getDeals().get(0).getResults().get(position).getMediumImageUrl();

        if (imageURI.isEmpty()) {
            imageURI = "R.drawable.blank_white.png";
        }

        Glide
                .with(context)
                .load(imageURI)
                .centerCrop()
                .placeholder(R.drawable.blank_white)
                .crossFade()
                .override(150,150)
                .into(holder.imageView);

    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.cardview_coupons, parent, false);
        RecyclerViewHolder vh = new RecyclerViewHolder(view);

        return vh;
    }

    @Override
    public int getItemCount() {

        return data.getDeals().size();
    }
}
