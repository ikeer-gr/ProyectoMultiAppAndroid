package com.example.ikergomezrubio;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppTiempo extends AppCompatActivity {

    private TextView weatherText, tvFeelsLike, tvWeatherDescription, tvWind, tvPressure, tvHumidity, tvVisibility;
    private final String keyAPI = "478cc0206bdb10ef811380d2355379a8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_tiempo);

        weatherText = findViewById(R.id.weatherText);
        tvFeelsLike = findViewById(R.id.tvFeelsLike);
        tvWeatherDescription = findViewById(R.id.tvWeatherDescription);
        tvWind = findViewById(R.id.tvWind);
        tvPressure = findViewById(R.id.tvPressure);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvVisibility = findViewById(R.id.tvVisibility);

        getWeatherData();
    }

    private void getWeatherData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioTiempo service = retrofit.create(ServicioTiempo.class);
        Call<RespuestaTiempo> call = service.getWeather("Murcia,ES", keyAPI, "es");

        call.enqueue(new Callback<RespuestaTiempo>() {
            @Override
            public void onResponse(Call<RespuestaTiempo> call, Response<RespuestaTiempo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RespuestaTiempo weather = response.body();

                    String temp = Math.round(weather.getMain().getTemp() - 273.15) + "°C";
                    weatherText.setText(temp);

                    String feelsLike = Math.round(weather.getMain().getFeelsLike() - 273.15) + "°C";
                    tvFeelsLike.setText("Sensación térmica: " + feelsLike);

                    String description = weather.getWeather().get(0).getDescription().toUpperCase();
                    tvWeatherDescription.setText(description);

                    String windSpeed = weather.getWind().getSpeed() + "m/s " + weather.getWind().getDirection();
                    tvWind.setText("Viento: " + windSpeed);

                    String pressure = weather.getMain().getPressure() + " hPa";
                    tvPressure.setText("Presión: " + pressure);

                    String humidity = weather.getMain().getHumidity() + "%";
                    tvHumidity.setText("Humedad: " + humidity);

                    String visibility = (weather.getVisibility() / 1000.0) + " km";
                    tvVisibility.setText("Visibilidad: " + visibility);

                } else {
                    weatherText.setText("ERROR NO HAY CONEXION");
                }
            }

            @Override
            public void onFailure(Call<RespuestaTiempo> call, Throwable t) {
                weatherText.setText("Error al obtener el tiempo");
            }
        });
    }
}
