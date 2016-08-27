package com.app.venki.up.providers;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yash on 8/20/2016.
 */
public interface CouponsGroupOn {
    //https://partner-api.groupon.com/deals.json?tsToken=US_AFF_0_201236_212556_0&division_id=chicago&filters=category:food-and-drink&offset=0&limit=50

    @GET("deals.json?")
    Call<ResponseBody> getCouponsGroupOn(@Query("tsToken") String token,
                                  @Query("division_id") String division,
                                  @Query("filters") String category,
                                  @Query("offset") String offset,
                                  @Query("limit") int limit

    );

}
