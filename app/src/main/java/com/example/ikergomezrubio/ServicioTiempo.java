package com.example.ikergomezrubio;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServicioTiempo {
    @GET("weather")
    Call<RespuestaTiempo> getWeather(
            @Query("q") String city,
            @Query("appid") String apiKey
    );
}