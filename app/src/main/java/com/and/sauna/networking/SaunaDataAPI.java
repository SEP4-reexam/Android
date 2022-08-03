package com.and.sauna.networking;

import com.and.sauna.model.HistoricalDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SaunaDataAPI {

    @Headers("")

    @GET("sauna")
    Call<HistoricalDataResponse> getHistoricalData (
            @Query("name") String saunaName,
            @Query("id") int id,
            @Query("timestamp") long timestamp,
            @Query("temperature") float temp,
            @Query("co2") float co2,
            @Query("humidity") float humidity

    );

}
