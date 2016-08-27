package com.app.venki.up.providers;

import com.app.venki.up.models.events.events.GoogleEvent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Billy on 8/9/16.
 */
public interface EventAPI {

    @GET("json?")
    Call<GoogleEvent> getGoogleEvents(@Query("location") String latLong,
                                      @Query("radius") String radius,
                                      @Query("name") String eventName,
                                      @Query("key") String key

    );

}
