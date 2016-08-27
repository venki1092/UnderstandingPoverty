package com.app.venki.up.providers;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by samsiu on 7/10/16.
 */
public interface HousingService {

    @GET("searchByLocation?")
    Call<ResponseBody> getHousingAgencies(@Query("Lat") String latitude,
                                          @Query("Long") String longitude,
                                          @Query("Distance") String distance,
                                          @Query("RowLimit") String rowLimit,
                                          @Query("Services") String services,
                                          @Query("Languages") String languages
    );
}
