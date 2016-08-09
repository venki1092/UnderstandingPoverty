package com.example.venki.up.providers;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Billy on 8/9/16.
 */
public interface CouponAPI {

    @GET("getdeals?")
    Call<ResponseBody> getCoupons(@Query("key") String key,
                                  @Query("zip") String zip,
                                  @Query("mileradius") String mile,
                                  @Query("limit") int limit,
                                  @Query("orderby") String radius,
                                  @Query("categoryid") String category
    );

}
