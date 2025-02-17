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

    private TextView weatherText, tvFeelsLike, tvWeatherDescription, tvWind, tvPressure, tvHumidity, tvUV, tvDewPoint, tvVisibility;
    private final String WEATHER_API_KEY = "478cc0206bdb10ef811380d2355379a8"; // Reemplaza con tu API key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_tiempo);

        // Referencias a los elementos del layout
        weatherText = findViewById(R.id.weatherText); // NO modificar
        tvFeelsLike = findViewById(R.id.tvFeelsLike);
        tvWeatherDescription = findViewById(R.id.tvWeatherDescription);
        tvWind = findViewById(R.id.tvWind);
        tvPressure = findViewById(R.id.tvPressure);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvDewPoint = findViewById(R.id.tvDewPoint);
        tvVisibility = findViewById(R.id.tvVisibility);

        // Obtener el clima de Murcia
        getWeatherData();
    }

    private void getWeatherData() {
        // Configuramos Retrofit para hacer la llamada API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioTiempo service = retrofit.create(ServicioTiempo.class);
        Call<RespuestaTiempo> call = service.getWeather("Murcia,ES", WEATHER_API_KEY);

        call.enqueue(new Callback<RespuestaTiempo>() {
            @Override
            public void onResponse(Call<RespuestaTiempo> call, Response<RespuestaTiempo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RespuestaTiempo weather = response.body();

                    // Convertimos la temperatura de Kelvin a Celsius y la redondeamos
                    String temp = Math.round(weather.getMain().getTemp() - 273.15) + "°C";
                    weatherText.setText("Temperatura en Murcia: " + temp); // MANTENEMOS ESTE

                    // Sensación térmica
                    String feelsLike = Math.round(weather.getMain().getFeelsLike() - 273.15) + "°C";
                    tvFeelsLike.setText("Feels like: " + feelsLike);

                    // Descripción del clima
                    String description = weather.getWeather().get(0).getDescription();
                    tvWeatherDescription.setText(description);

                    // Viento (velocidad y dirección)
                    String windSpeed = weather.getWind().getSpeed() + "m/s " + weather.getWind().getDirection();
                    tvWind.setText("Viento: " + windSpeed);

                    // Presión atmosférica
                    String pressure = weather.getMain().getPressure() + " hPa";
                    tvPressure.setText("Presión: " + pressure);

                    // Humedad
                    String humidity = weather.getMain().getHumidity() + "%";
                    tvHumidity.setText("Humedad: " + humidity);

                                       // Punto de rocío (Dew Point)
                    String dewPoint = Math.round(weather.getMain().getDewPoint() - 273.15) + "°C";
                    tvDewPoint.setText("Punto de rocío: " + dewPoint);

                    // Visibilidad en km
                    String visibility = (weather.getVisibility() / 1000.0) + " km";
                    tvVisibility.setText("Visibilidad: " + visibility);

                } else {
                    weatherText.setText("Error al obtener el clima");
                }
            }

            @Override
            public void onFailure(Call<RespuestaTiempo> call, Throwable t) {
                weatherText.setText("Error al obtener el clima");
            }
        });
    }
}
