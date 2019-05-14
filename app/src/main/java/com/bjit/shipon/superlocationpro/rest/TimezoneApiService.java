package com.bjit.shipon.superlocationpro.rest;

import com.bjit.shipon.superlocationpro.model.TimeZoneList;
import com.bjit.shipon.superlocationpro.model.Timezone;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TimezoneApiService {

    @GET("get-time-zone")
    Call<Timezone> getTimezone(@Query("key") String API_KEY,
                               @Query("format") String RESPONSE_FORMAT,
                               @Query("by") String responseType,
                               @Query("lat") double latitude,
                               @Query("lng") double longitude);

    @GET("list-time-zone")
    Call<TimeZoneList> getAllTimeZone(@Query("key") String API_KEY,
                                      @Query("format") String RESPONSE_FORMAT);
}
